package me.yangsongi.electrozone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.yangsongi.electrozone.domain.Category;
import me.yangsongi.electrozone.domain.Product;
import me.yangsongi.electrozone.dto.AddProductRequest;
import me.yangsongi.electrozone.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    // dataset내부의 json파일을 읽어들여 상품에 반영합니다.
    @Transactional
    public void reflectCrawledData(String datasetPath) throws IOException {
        // JSON 파일 읽기
        File datasetDir = Paths.get(datasetPath).toFile();
        File[] jsonFiles = datasetDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (jsonFiles == null) return;

        for (File jsonFile : jsonFiles) {
            // 파일명에서 카테고리 추출 (파일명에서 .json 확장자를 제외한 부분)
            String categoryName = jsonFile.getName().replace(".json", "").toUpperCase();
            // Category enum 값으로 변환 (예외처리)
            Category category = Optional.of(Category.valueOf(categoryName))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category found in file name: " + categoryName));
            List<AddProductRequest> products = objectMapper.readValue(jsonFile, new TypeReference<>() {});
            products.forEach(product -> saveOrUpdateProduct(product, category));
        }
    }

    // 상품 id가 이미 존재하면 업데이트, 없으면 새로 생성합니다.
    private void saveOrUpdateProduct(AddProductRequest request, Category category) {
        // registration_date를 String에서 LocalDateTime으로 변환
        LocalDateTime registeredAt = parseRegistrationDate(request.registeredAt());

        // id 자체가 null로 들어오면 수행하지 않습니다.
        if (request.productId() != null) {
            Optional<Product> product = productRepository.findById(request.productId());
            if (product.isPresent()) {
                product.get().update(request, registeredAt);
            } else {
                Product newProduct = Product.builder()
                        .productId(request.productId())
                        .name(request.name())
                        .price(request.price())
                        .specs(request.specs().toString())
                        .imageUrl(request.imageUrl())
                        .stock(100) // 기본 재고 값
                        .registeredAt(registeredAt)
                        .category(category)
                        .build();
                productRepository.save(newProduct);
            }
        }
    }

    // 등록일 String을 LocalDateTime으로 변환하는 메소드
    private LocalDateTime parseRegistrationDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null; // 유효하지 않은 경우 null 반환
        }

        char lastChar = dateString.charAt(dateString.length()-1);
        // "등록월"만 제거하고, 뒤에 "01"을 추가
        // 등록일에 대한 값이 2022.11 혹은 2022.11. 으로 반환되기 때문에 이를 분기했습니다.
        String formattedDate = switch (lastChar) {
            case '.' -> dateString.replaceAll("등록월", "").trim() + "01 00:00:00";
            default -> dateString.replaceAll("등록월", "").trim() + ".01 00:00:00";
        };

        // "2020.07.01" 형식으로 변환한 후, 원하는 형식으로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        // 날짜를 1일 00시 00분 00초로 설정
        return LocalDateTime.parse(formattedDate, formatter); // 기본적으로 1일 00시로 설정
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + productId));
    }

    // 최신 상품 6개를 가져옵니다.
    public List<Product> getTop6LatestProducts() {
        return productRepository.findTop6ByOrderByRegisteredAtDesc();
    }

    // 인기상품 6개를 가져옵니다.
    public List<Product> getTop12PopularProducts(Category category) {
        return productRepository.findTop12ByCategory(category);
    }

}

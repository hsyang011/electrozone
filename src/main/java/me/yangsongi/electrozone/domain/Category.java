package me.yangsongi.electrozone.domain;

import lombok.Getter;

@Getter
public enum Category {

    // 컴퓨터 부품 대분류
    COMPUTER_PARTS("컴퓨터 부품", SubCategory.COMPUTER_PARTS),

    // 노트북/PC 대분류
    LAPTOP_PC("노트북/PC", SubCategory.LAPTOP_PC),

    // 모니터 대분류
    MONITOR("모니터", SubCategory.MONITOR),

    // 오디오/음향기기 대분류
    AUDIO("오디오/음향기기", SubCategory.AUDIO),

    // 주변기기 대분류
    PERIPHERALS("주변기기", SubCategory.PERIPHERALS),

    // 게임 대분류
    GAMING("게임", SubCategory.GAMING),

    // 스마트기기 대분류
    SMART_DEVICES("스마트기기", SubCategory.SMART_DEVICES),

    // 네트워크 장비 대분류
    NETWORK("네트워크 장비", SubCategory.NETWORK),

    // 가전제품 대분류
    HOME_APPLIANCES("가전제품", SubCategory.HOME_APPLIANCES);

    private final String categoryName;
    private final SubCategory subCategory;

    Category(String categoryName, SubCategory subCategory) {
        this.categoryName = categoryName;
        this.subCategory = subCategory;
    }

    // 소분류 enum
    public enum SubCategory {
        COMPUTER_PARTS("CPU", "그래픽카드", "메인보드", "RAM", "SSD/HDD", "파워 서플라이", "케이스"),
        LAPTOP_PC("데스크탑 PC", "노트북", "미니PC", "올인원PC"),
        MONITOR("LCD/LED 모니터", "게이밍 모니터", "4K 모니터"),
        AUDIO("헤드폰/이어폰", "스피커", "마이크", "블루투스 기기"),
        PERIPHERALS("키보드", "마우스", "웹캠", "외장 하드/USB", "프린터"),
        GAMING("게이밍 기기", "콘솔 게임", "게임 액세서리"),
        SMART_DEVICES("스마트폰", "스마트워치", "스마트홈"),
        NETWORK("라우터", "스위치", "공유기"),
        HOME_APPLIANCES("TV", "냉장고", "청소기", "공기청정기");

        private final String[] subCategories;

        SubCategory(String... subCategories) {
            this.subCategories = subCategories;
        }

        public String[] getSubCategories() {
            return subCategories;
        }
    }

    // 예시로 카테고리 출력 메서드
    public static void printCategoryInfo() {
        for (Category category : Category.values()) {
            System.out.println("대분류: " + category.getCategoryName());
            System.out.println("소분류: ");
            for (String subCategory : category.getSubCategory().getSubCategories()) {
                System.out.println(" - " + subCategory);
            }
            System.out.println();
        }
    }

}

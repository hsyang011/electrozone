//package me.yangsongi.electrozone.scheduler;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import me.yangsongi.electrozone.service.ProductService;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j(topic = "Scheduler")
//@Component
//@RequiredArgsConstructor
//public class CrawlingScheduler {
//
//    private final ProductService productService;
//
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 12시
//    public void executePythonScript() {
//        // 파이썬 실행 파일 경로
//        String pythonScriptPath = "scripts/crawler.exe"; // 파이썬 크롤링 실행 프로그램 경로
//        String datasetPath = "dataset"; // 크롤러에 의해 저장될 디렉토리 경로
//
//        // ProcessBuilder를 사용하여 실행
//        ProcessBuilder processBuilder = new ProcessBuilder();
//
//        // 현재 작업 디렉토리 설정 (스프링 부트 프로젝트 루트 디렉토리)
//        processBuilder.directory(new File(System.getProperty("user.dir")));
//
//        // 파이썬 실행 파일 명령 설정
//        List<String> command = new ArrayList<>();
//        command.add(pythonScriptPath); // 실행 파일
//        processBuilder.command(command);
//
//        try {
//            System.out.println("Python script will start.");
//            // 프로세스 시작
//            Process process = processBuilder.start();
//
//            // 1분 대기 후 강제 종료
//            boolean finished = process.waitFor(1, java.util.concurrent.TimeUnit.MINUTES);
//            if (!finished) {
//                process.destroy(); // 프로세스를 강제 종료
//                System.out.println("Python script timed out and was terminated.");
//            } else {
//                int exitCode = process.exitValue();
//                System.out.println("Python script exited with code: " + exitCode);
//            }
//
//            productService.reflectCrawledData(datasetPath);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//}

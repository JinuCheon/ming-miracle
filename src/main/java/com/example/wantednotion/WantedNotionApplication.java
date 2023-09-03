package com.example.wantednotion;

import com.example.wantednotion.dto.PageResponseDto;
import com.example.wantednotion.service.PageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class WantedNotionApplication {
	public static void main(String[] args) {
//		SpringApplication.run(WantedNotionApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(WantedNotionApplication.class, args);

		PageService pageService = applicationContext.getBean(PageService.class);

		// 확인
		// 1b3b692c-6ed5-4db9-bf7a-0470706b0423
		// 2c8015a3-76f0-4d2d-afed-4b7fb8ea8527
		String testId = "45e4609d-726d-4703-9242-18e86f6b9a0b";
		PageResponseDto result = pageService.findPageById(testId);

		try {
			// json
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			String jsonBreadcrumbs = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pageService.getBreadcrumbs(testId));

			log.info("response = \n" + jsonResponse);
			log.info("\njsonBreadcrumbs = \n" + jsonBreadcrumbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

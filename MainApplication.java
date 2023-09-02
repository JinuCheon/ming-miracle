import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.DatabaseManager;
import java.util.List;
import model.Page;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.PageRepository;
import service.PageService;

public class MainApplication {

    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(MainApplication.class);

        // DatabaseManager를 사용하여 데이터베이스 연결 풀 생성
        JdbcConnectionPool connectionPool = DatabaseManager.getConnectionPool();

        // Repository 및 Service 초기화
        PageRepository pageRepository = new PageRepository(connectionPool);
        PageService pageService = new PageService(pageRepository);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        // 특정 페이지의 정보 조회
        String pageId = "45e4609d-726d-4703-9242-18e86f6b9a0b"; //페이지 1
        //String pageId = "79f2275d-68c7-4b24-9d5f-d741a344a4a5"; //페이지 1의 서브페이지 1
        //String pageId = "a1e3db98-e184-48ea-8ca2-e30c0a90f33c"; //페이지 1의 서브페이지 1의 서브페이지 1
        //String pageId = "a96c3191-9b02-463e-b245-60c2010499f3"; //페이지 1의 서브페이지 1의 서브페이지 1의 서브페이지 1

        Page page = pageService.getPageInfo(pageId);
        if (page != null) {
            try {
                String pageJson = objectWriter.writeValueAsString(page);
                log.info("Page Info={}", pageJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("{\"error\": \"페이지를 찾을 수 없습니다.\"}");
        }

        // 전체 페이지 목록 조회
        List<Page> allPages = pageService.getAllPages();
        if (!allPages.isEmpty()) {
            try {
                String allPagesJson = objectWriter.writeValueAsString(allPages);
                log.info("All Pages={}", allPagesJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("{\"error\": \"페이지를 찾을 수 없습니다.\"}");
        }

        // 연결 종료
        DatabaseManager.closeConnectionPool();
    }
}
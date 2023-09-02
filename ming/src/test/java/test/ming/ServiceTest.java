package test.ming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
    시험 데이터 (DB)
    INSERT INTO PAGE (id, title, content, parentId)
VALUES ('1', '페이지 1', '페이지 1의 내용', NULL),
       ('2', '페이지 2', '페이지 2의 내용', '1'),
       ('3', '페이지 3', '페이지 3의 내용', '1'),
       ('4', '페이지 4', '페이지 4의 내용', '1'),
       ('5', '페이지 5', '페이지 5의 내용', '2'),
       ('6', '페이지 6', '페이지 6의 내용', '3'),
       ('7', '페이지 7', '페이지 7의 내용', '2'),
       ('8', '페이지 8', '페이지 8의 내용', '3');
 */
class ServiceTest {
    private final Mapper mapper = new Mapper();
    private final Repository repository = new Repository();
    private final Service pageService = new Service(repository, mapper);

    @Test
    @DisplayName("PageId : 1 테스트 -> subPages 만 1,2,3")
    public void testGetPageInfo1() {
        String pageId = "1";

        PageDto.Response response = pageService.getPageInfo(pageId);

        assertNotNull(response);

        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("PageId : 8 테스트 -> breadcrumbs 만 1 / 3")
    public void testGetPageInfo2() {
        String pageId = "8";

        PageDto.Response response = pageService.getPageInfo(pageId);

        assertNotNull(response);

        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("PageId : 3 테스트 -> subPage : 6 , 8 | breadcrumbs 1")
    public void testGetPageInfo3() {
        String pageId = "3";

        PageDto.Response response = pageService.getPageInfo(pageId);

        assertNotNull(response);

        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
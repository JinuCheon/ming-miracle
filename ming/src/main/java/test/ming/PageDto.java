package test.ming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class PageDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private final String id;
        private final String title;
        private final String content;
        private final List<Info> subPages;
        private final List<Info> breadcrumbs;
    }

    @Getter
    @AllArgsConstructor
    public static class Info {
        private final String id;
        private final String title;
    }

    @Getter
    @AllArgsConstructor
    public static class InfoParent {
        private final String id;
        private final String title;
        private final String parentId;
    }

}

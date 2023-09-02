package test.ming;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    PageDto.Response pageToResponse(Page page, List<PageDto.Info> subPages, List<PageDto.Info> breadcrumbs) {
        return new PageDto.Response(
                page.getId(),
                page.getTitle(),
                page.getContent(),
                subPages,
                breadcrumbs
        );
    }

    PageDto.Info pageInfoToPageInfoParent(PageDto.InfoParent infoParent) {
        return new PageDto.Info(infoParent.getId(), infoParent.getTitle());
    }
}

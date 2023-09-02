package test.ming;

import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Service {
    private final Repository repository;
    private final Mapper mapper;

    public PageDto.Response getPageInfo(String pageId) {
        Page page = repository.findById(pageId);

        List<PageDto.Info> subPages = repository.findAllByParentId(pageId);

        List<PageDto.Info> breadcrumbs = new ArrayList<>();

        if (page.getParentId() != null) {
            getBreadcrumbs(page.getParentId(), breadcrumbs);
        }

        return mapper.pageToResponse(page, subPages, breadcrumbs);
    }

    public void getBreadcrumbs(String pageId, List<PageDto.Info> breadcrumbs) {
        PageDto.InfoParent infoParent = repository.findPageInfoById(pageId);
        if (infoParent == null) {
            return;
        } else if (infoParent.getId() == null) {
            breadcrumbs.add(0, mapper.pageInfoToPageInfoParent(infoParent));
            return;
        }
        breadcrumbs.add(0, mapper.pageInfoToPageInfoParent(infoParent));
        getBreadcrumbs(infoParent.getParentId(), breadcrumbs);
    }
}
package org.example.domain;

import java.util.List;

public interface PageRepository {

    Page findById(String id);

    List<Page> findAllByParentId(String parentId);
}

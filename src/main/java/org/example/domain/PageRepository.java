package org.example.domain;

import java.util.List;
import java.util.Optional;

public interface PageRepository {

    Optional<Page> findById(String id);

    List<Page> findAllByParentId(String parentId);
}

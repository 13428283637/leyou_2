package com.leyou.service.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryByParentId(Long pid);
}

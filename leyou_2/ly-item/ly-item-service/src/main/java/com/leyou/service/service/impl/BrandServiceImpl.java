package com.leyou.service.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Category;
import com.leyou.service.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.service.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Category> queryCategoryByBid(Long bid) {
        brandMapper.queryCategoryByBid(bid);
        return null;
    }

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        log.error("----------------------------------------------");
        log.error(page+"");
        log.error(rows+"");
        log.error("----------------------------------------------");
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            String sortByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(sortByClause);
        }
        //开启分页
//        PageHelper.startPage(page, rows);
        Page<Object> page1 = PageHelper.startPage(1, 5,true);
        List<Brand> brandList = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        return new PageResult<>(pageInfo.getTotal(), brandList);

        //        PageHelper.startPage(page,rows);
//        System.out.println("--------------------------");
//        System.out.println(page);
//        System.out.println(rows);
//        System.out.println("--------------------------");
//
//        Example example = new Example(Brand.class);
//        if(StringUtils.isNotBlank(key)){
//            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key);
//        }
//
//        if(StringUtils.isNotBlank(sortBy)){
//            example.setOrderByClause(sortBy+(desc ? " DESC" : " ASC"));
//        }
//
//        List<Brand> brandList = brandMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(brandList)) {
//            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
//        }
//
//        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
//
//        return new PageResult<>(pageInfo.getTotal(), brandList);


    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int resultCount = brandMapper.insert(brand);
        if(resultCount==0){
            throw new LyException(ExceptionEnum.BRAND_CREATE_FAILED);
        }
        for(Long cid : cids){
            log.error("--------------cid------------"+cid+"--------------------------");
            log.error("--------------bid------------"+brand.getId()+"--------------------------");
            resultCount = brandMapper.saveCategoryBrand(cid, brand.getId());
            if(resultCount==0){
                throw new LyException(ExceptionEnum.BRAND_CREATE_FAILED);
            }

        }


    }


}

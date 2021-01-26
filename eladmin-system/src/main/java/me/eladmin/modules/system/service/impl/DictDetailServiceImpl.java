/*
*  Copyright 2019-2020 eladmin
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.eladmin.modules.system.service.impl;

import me.eladmin.modules.system.domain.DictDetail;
import me.eladmin.utils.ValidationUtil;
import me.eladmin.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.DictDetailRepository;
import me.eladmin.modules.system.service.DictDetailService;
import me.eladmin.modules.system.service.dto.DictDetailDto;
import me.eladmin.modules.system.service.dto.DictDetailQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.DictDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.eladmin.utils.PageUtil;
import me.eladmin.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author el
* @date 2021-01-26
**/
@Service
@RequiredArgsConstructor
public class DictDetailServiceImpl implements DictDetailService {

    private final DictDetailRepository dictDetailRepository;
    private final DictDetailMapper dictDetailMapper;

    @Override
    public Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable){
        Page<DictDetail> page = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    public List<DictDetailDto> queryAll(DictDetailQueryCriteria criteria){
        return dictDetailMapper.toDto(dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DictDetailDto findById(Long id) {
        DictDetail dictDetail = dictDetailRepository.findById(id).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(),"DictDetail","id",id);
        return dictDetailMapper.toDto(dictDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDto create(DictDetail resources) {
        return dictDetailMapper.toDto(dictDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        DictDetail dictDetail = dictDetailRepository.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull( dictDetail.getId(),"DictDetail","id",resources.getId());
        dictDetail.copy(resources);
        dictDetailRepository.save(dictDetail);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            dictDetailRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DictDetailDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DictDetailDto dictDetail : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("字典id", dictDetail.getDictId());
            map.put("字典标签", dictDetail.getLabel());
            map.put("字典值", dictDetail.getValue());
            map.put("排序", dictDetail.getDictSort());
            map.put("创建者", dictDetail.getCreateBy());
            map.put("更新者", dictDetail.getUpdateBy());
            map.put("创建日期", dictDetail.getCreateTime());
            map.put("更新时间", dictDetail.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
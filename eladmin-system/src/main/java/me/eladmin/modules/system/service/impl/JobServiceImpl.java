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

import me.eladmin.modules.system.domain.Job;
import me.eladmin.exception.EntityExistException;
import me.eladmin.utils.ValidationUtil;
import me.eladmin.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.eladmin.modules.system.repository.JobRepository;
import me.eladmin.modules.system.service.JobService;
import me.eladmin.modules.system.service.dto.JobDto;
import me.eladmin.modules.system.service.dto.JobQueryCriteria;
import me.eladmin.modules.system.service.mapstruct.JobMapper;
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
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public Map<String,Object> queryAll(JobQueryCriteria criteria, Pageable pageable){
        Page<Job> page = jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(jobMapper::toDto));
    }

    @Override
    public List<JobDto> queryAll(JobQueryCriteria criteria){
        return jobMapper.toDto(jobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public JobDto findById(Long id) {
        Job job = jobRepository.findById(id).orElseGet(Job::new);
        ValidationUtil.isNull(job.getId(),"Job","id",id);
        return jobMapper.toDto(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobDto create(Job resources) {
        if(jobRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Job.class,"name",resources.getName());
        }
        return jobMapper.toDto(jobRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
        Job job = jobRepository.findById(resources.getId()).orElseGet(Job::new);
        ValidationUtil.isNull( job.getId(),"Job","id",resources.getId());
        Job job1 = null;
        job1 = jobRepository.findByName(resources.getName());
        if(job1 != null && !job1.getId().equals(job.getId())){
            throw new EntityExistException(Job.class,"name",resources.getName());
        }
        job.copy(resources);
        jobRepository.save(job);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            jobRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<JobDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JobDto job : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("岗位名称", job.getName());
            map.put("岗位状态", job.getEnabled());
            map.put("排序", job.getJobSort());
            map.put("创建者", job.getCreateBy());
            map.put("更新者", job.getUpdateBy());
            map.put("创建日期", job.getCreateTime());
            map.put("更新时间", job.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
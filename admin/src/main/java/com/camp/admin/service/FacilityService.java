package com.camp.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camp.admin.DTO.FacilityDTO;
import com.camp.admin.frame.MyService;
import com.camp.admin.mapper.FacilityMapper;

@Service
public class FacilityService implements MyService<Integer, FacilityDTO> {
    @Autowired
    FacilityMapper mapper;

    @Override
    public void insert(FacilityDTO facilityDTO) throws Exception {
        mapper.insert(facilityDTO);
    }

    @Override
    public void delete(Integer integer) throws Exception {
        mapper.delete(integer);
    }

    @Override
    public void update(FacilityDTO facilityDTO) throws Exception {
        mapper.update(facilityDTO);
    }

    @Override
    public FacilityDTO select(Integer integer) throws Exception {
        return mapper.select(integer);
    }

    @Override
    public List<FacilityDTO> selectAll() throws Exception {
        return mapper.selectAll();
    }

    public List<FacilityDTO> selectByCompany(int company_code){
        return mapper.selectByCompany(company_code);
    }
}

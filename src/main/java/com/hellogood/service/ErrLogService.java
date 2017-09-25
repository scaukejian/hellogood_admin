package com.hellogood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.ErrLog;
import com.hellogood.mapper.ErrLogMapper;

/**
 * Created by 错误日志记录 on 2017/9/17.
 */
@Service
@Transactional
public class ErrLogService {
    @Autowired
    private ErrLogMapper errLogMapper;

    public void add(ErrLog errLog){
        errLogMapper.insert(errLog);
    }
}

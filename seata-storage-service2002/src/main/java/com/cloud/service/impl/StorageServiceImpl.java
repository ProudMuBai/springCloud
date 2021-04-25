package com.cloud.service.impl;

import com.cloud.dao.StorageDao;
import com.cloud.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        LOGGER.info("----------->storage-service ： 扣减库存开始");
        storageDao.decrease(productId,count);
        LOGGER.info("----------->storage-service ： 库存扣减结束");

    }
}

package com.cloud.service;


public interface StorageService {
    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 扣减的产品数量
     */
    void  decrease(Long productId,Integer count);
}

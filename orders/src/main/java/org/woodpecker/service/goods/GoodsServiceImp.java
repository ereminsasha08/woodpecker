package org.woodpecker.service.goods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.woodpecker.repository.GoodsRepository;
import org.woodpecker.repository.model.goods.Good;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsServiceImp implements GoodsService {

    private final GoodsRepository goodsRepository;
    @Override
    public List<Good> getAll() {
        log.info("Controller getAll");
        return goodsRepository.findAll();
    }
    @Override
    public Good getById(Integer id) {
        return goodsRepository.getExisted(id);
    }
    @Override
    public Good create(Good good) {
        return goodsRepository.create(good);
    }
    @Override
    public Good update(Good updated, Integer id) {
        return goodsRepository.update(updated, id);
    }
    @Override
    public void deleteById(Integer id) {
        goodsRepository.deleteExisted(id);
    }
}

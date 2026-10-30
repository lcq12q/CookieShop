package xyz.lcq12q.service;

import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.utils.Page;

import java.util.List;

public interface IGoodsService {
	// 按照推荐类型分页查询商品信息，返回Page<Goods>对象
	/**
	 * 查询推荐类型商品
	 * @param type 推荐类型
	 * @param pageNumber 页码
	 * @param pageSize 煤业大小
	 * @return 推荐类型商品页
	 */
	Page<Goods> queryByRecommendType(int type, int pageNumber, int pageSize);

	// 查询所有滚动推荐商品
	List<Goods> queryAllScrollGoods();

	// 查询所有热销推荐商品
	List<Goods> queryAllHotGoods();

	// 查询所有新品推荐商品
	List<Goods> queryAllAnewGoods();

	// 通过id查询商品信息
	Goods queryById(int id);

	// 根据关键词模糊查询商品列表（带分页）
	Page<Goods> queryByKeyword(String keyword, int pageNumber, int pageSize);

	// 分页查询所有商品
	Page<Goods> queryAll(int pageNumber, int pageSize);

	// 根据类型分页查询商品
	Page<Goods> queryByTypeId(int typeId, int pageNumber, int pageSize);

	// 通过id查询商品信息
	Page<Goods> queryGoodsByTypeId(int typeid, int pageNumber, int pageSize);
}

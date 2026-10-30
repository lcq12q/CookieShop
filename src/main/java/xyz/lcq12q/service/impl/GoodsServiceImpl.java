package xyz.lcq12q.service.impl;

import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.dao.IGoodsDao;
import xyz.lcq12q.dao.impl.GoodsDaoImpl;
import xyz.lcq12q.service.IGoodsService;
import xyz.lcq12q.utils.Page;

import java.util.ArrayList;
import java.util.List;

public class GoodsServiceImpl implements IGoodsService {
	private IGoodsDao goodsDao = new GoodsDaoImpl();

	/**
	 * 查询推荐类型商品
	 * @param type       推荐类型
	 * @param pageNumber 页码
	 * @param pageSize   煤业大小
	 * @return 推荐类型商品页
	 */
	@Override
	public Page<Goods> queryByRecommendType(int type, int pageNumber, int pageSize) {
		Page<Goods> page = new Page<>();
		// 设置页码和页大小
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		// 查询总记录数
		int totalCount = goodsDao.countByRecommendType(type);
		page.setTotalCount(totalCount);
		if (totalCount > 0) {
			// 查询当前页数据
			List<Goods> goodsList = goodsDao.queryByRecommendType(type, pageNumber, pageSize);
			page.setList(goodsList);
		} else {
			page.setList(new ArrayList<>());
		}
		return page; // 始终返回 page，避免调用方因 null 引发空指针
	}

	/**
	 * 查询所有滚动推荐商品
	 * @return
	 */
	@Override
	public List<Goods> queryAllScrollGoods() {
		List<Goods> goodsList = goodsDao.queryAllByRecommendId(1);
		return goodsList;
	}

	/**
	 * 查询所有热销推荐商品
	 * @return
	 */
	@Override
	public List<Goods> queryAllHotGoods() {
		List<Goods> goodsList = goodsDao.queryAllByRecommendId(2);
		return goodsList;
	}

	/**
	 * 查询所有新品推荐商品
	 * @return
	 */
	@Override
	public List<Goods> queryAllAnewGoods() {
		List<Goods> goodsList = goodsDao.queryAllByRecommendId(3);
		return goodsList;
	}

	/**
	 * 通过id查询商品信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Goods queryById(int id) {
		return goodsDao.queryById(id);
	}

	/**
	 * 根据关键词模糊查询商品列表（带分页）
	 * @param keyword    关键字
	 * @param pageNumber 页码
	 * @param pageSize   每页数量
	 * @return 商品列表分页对象
	 */
	@Override
	public Page<Goods> queryByKeyword(String keyword, int pageNumber, int pageSize) {
		// 查询总记录数
		int totalCount = goodsDao.countByKeyword(keyword);
		if (totalCount > 0) {
			// 查询当前页数据
			List<Goods> goodsList = goodsDao.queryByKeyword(keyword, pageNumber, pageSize);
			Page<Goods> page = new Page<>();
			page.setPageNumber(pageNumber);
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);
			page.setList(goodsList);
			return page;
		} else {
			// 返回空的分页对象，避免返回 null
			Page<Goods> page = new Page<>();
			page.setPageNumber(pageNumber);
			page.setPageSize(pageSize);
			page.setTotalCount(0);
			page.setList(new ArrayList<>());
			return page;
		}
	}

	/**
	 * 分页查询所有商品
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表分页对象
	 */
	@Override
	public Page<Goods> queryAll(int pageNumber, int pageSize) {
		Page<Goods> page = new Page<>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		int totalCount = goodsDao.countAll();
		page.setTotalCount(totalCount);
		if (totalCount > 0) {
			page.setList(goodsDao.queryAll(pageNumber, pageSize));
		} else {
			page.setList(new ArrayList<>());
		}
		return page;
	}

	/**
	 * 根据类型分页查询商品
	 * @param typeId 类型id
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表分页对象
	 */
	@Override
	public Page<Goods> queryByTypeId(int typeId, int pageNumber, int pageSize) {
		Page<Goods> page = new Page<>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		int totalCount = goodsDao.countByTypeId(typeId);
		page.setTotalCount(totalCount);
		if (totalCount > 0) {
			page.setList(goodsDao.queryByTypeId(typeId, pageNumber, pageSize));
		} else {
			page.setList(new ArrayList<>());
		}
		return page;
	}

	/**
	 * 通过id查询商品信息
	 * @param typeid 类型id
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表分页对象
	 */
	@Override
	public Page<Goods> queryGoodsByTypeId(int typeid, int pageNumber, int pageSize) {
		Page<Goods> page = new Page<>();
		// 设置分页信息
		page.setPageSize(pageSize);
		page.setPageNumber(pageNumber);
		// 查询总记录数
		int totalCount = goodsDao.queryByTypeIdCount(typeid);
		page.setTotalCount(totalCount);
		// 查询当前页数据
		if (totalCount > 0) {
			page.setList(goodsDao.queryByTypeId(typeid, pageNumber, pageSize));
		} else {
			page.setList(new ArrayList<>());
		}
		return page;
	}

	void main() {
		// 测试分页功能
		GoodsServiceImpl goodsService = new GoodsServiceImpl();
		Page<Goods> page = goodsService.queryGoodsByTypeId(1, 1, 5);
		System.out.println(page);
		System.out.println("总记录数: " + page.getTotalCount());
		System.out.println("当前页码: " + page.getPageNum());
		System.out.println("每页记录数: " + page.getPageSize());
		System.out.println("总页数: " + page.getTotalPage());
		for (Goods goods : page.getList()) {
			System.out.println(goods);
		}
	}
}

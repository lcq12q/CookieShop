package xyz.lcq12q.dao;

import com.mysql.jdbc.Connection;
import xyz.lcq12q.bean.Goods;

import java.util.List;

public interface IGoodsDao {
	// 通过商品id修改商品库存
	int updateGoodsStock(Connection conn,int id,int stock);
	// 分页查询推荐类型商品，给type、pageNumber、pageSize，查询推荐类型商品
	List<Goods> queryByRecommendType(int type, int pageNumber, int pageSize);

	// 分页查询推荐类型商品的总记录数
	int queryByRecommendTypeCount(int type);

	// 通过推荐类型id，查询所有商品
	List<Goods> queryAllByRecommendId(int recommendId);

	Goods queryById(int id);

	int countByRecommendType(int type);

	// 根据关键词模糊查询商品列表（带分页）
	List<Goods> queryByKeyword(String keyword, int pageNumber, int pageSize);

	// 根据关键词查询商品总数
	int countByKeyword(String keyword);

	// 分页查询所有商品
	List<Goods> queryAll(int pageNumber, int pageSize);

	// 查询所有商品总数
	int countAll();

	// 根据类型id分页查询商品
	List<Goods> queryByTypeId(int typeId, int pageNumber, int pageSize);

	// 根据类型id查询商品总数
	int countByTypeId(int typeId);

	// 分页查询商品信息的总记录数
	int queryByTypeIdCount(int typeid);

	void updateGoods(Goods goods);
}
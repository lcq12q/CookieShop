package xyz.lcq12q.dao.impl;

import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.bean.Type;
import xyz.lcq12q.dao.IGoodsDao;
import xyz.lcq12q.utils.JDBCUtils;
import xyz.lcq12q.utils.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDaoImpl implements IGoodsDao {
	/**
	 * 通过推荐类型查询商品列表
	 * @param recommendId 推荐类型id
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表
	 */
	@Override
	public List<Goods> queryByRecommendType(int type, int pageNumber, int pageSize) {
		List<Goods> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT goods.id, goods.`name`, goods.cover, goods.image1, goods.image2, goods.price, goods.intro, goods.stock, goods.type_id, type.`name` AS type_name, recommend.type FROM recommend INNER JOIN goods ON recommend.goods_id = goods.id INNER JOIN type ON goods.type_id = type.id WHERE recommend.type = ? LIMIT ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type);
			pstmt.setInt(2, (pageNumber - 1) * pageSize);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCover(rs.getString("cover"));
				g.setImage1(rs.getString("image1"));
				g.setImage2(rs.getString("image2"));
				g.setPrice(rs.getDouble("price"));
				g.setIntro(rs.getString("intro"));
				g.setStock(rs.getInt("stock"));

				// 设置商品类型
				Type t = new Type();
				t.setId(rs.getInt("type_id"));
				t.setName(rs.getString("type_name"));
				g.setType(t); // 将 type 对象设置到 goods 对象中

				switch (type) {
					case 1:
						g.setScoll(true);
						break;
					case 2:
						g.setHot(true);
						break;
					case 3:
						g.setAnew(true);
						break;
				}
				goodsList.add(g); // 💡 修正：使用正确的集合变量名 goodsList
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 💡 规范关闭数据库连接资源
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.close(rs,pstmt,conn);
		}
		return goodsList; // 💡 修正：返回填充好数据的列表
	}

	/**
	 * 通过推荐类型查询商品总数
	 * @param type 推荐类型id
	 * @return 商品总数
	 */
	@Override
	public int queryByRecommendTypeCount(int type) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT  Count(goods.id) FROM goods INNER JOIN recommend ON recommend.goods_id = goods.id INNER JOIN type ON goods.type_id = type.id WHERE recommend.type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	/**
	 * 通过推荐类型id，查询所有商品
	 * @param recommendId
	 * @return
	 */
	@Override
	public List<Goods> queryAllByRecommendId(int recommendId) {
		List<Goods> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT goods.id, goods.`name`, goods.cover, goods.image1, goods.image2, goods.price, goods.intro, goods.stock, goods.type_id, type.`name` AS type_name, recommend.type FROM recommend INNER JOIN goods ON recommend.goods_id = goods.id INNER JOIN type ON goods.type_id = type.id WHERE recommend.type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCover(rs.getString("cover"));
				g.setImage1(rs.getString("image1"));
				g.setImage2(rs.getString("image2"));
				g.setPrice(rs.getDouble("price"));
				g.setIntro(rs.getString("intro"));
				g.setStock(rs.getInt("stock"));

				// 设置商品类型
				Type type = new Type();
				type.setId(rs.getInt("type_id"));
				type.setName(rs.getString("type_name"));
				g.setType(type); // 将 type 对象设置到 goods 对象中

				switch (recommendId) {
					case 1:
						g.setScoll(true);
						break;
					case 2:
						g.setHot(true);
						break;
					case 3:
						g.setAnew(true);
						break;
				}
				goodsList.add(g); // 💡 修正：使用正确的集合变量名 goodsList
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 💡 规范关闭数据库连接资源
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.close(rs,pstmt,conn);
		}
		return goodsList; // 💡 修正：返回填充好数据的列表
	}

	/**
	 * 通过商品id查询商品
	 * @param id
	 * @return
	 */
	@Override
	public Goods queryById(int id) {
		Goods goods = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT goods.id, goods.`name`, goods.cover, goods.image1, goods.image2, goods.price, goods.intro, goods.stock, goods.type_id, type.`name` AS type_name FROM goods INNER JOIN type ON goods.type_id = type.id WHERE goods.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				goods = new Goods();
				goods.setId(rs.getInt("id"));
				goods.setName(rs.getString("name"));
				goods.setCover(rs.getString("cover"));
				goods.setImage1(rs.getString("image1"));
				goods.setImage2(rs.getString("image2"));
				goods.setPrice(rs.getDouble("price"));
				goods.setIntro(rs.getString("intro"));
				goods.setStock(rs.getInt("stock"));

				// 设置商品类型
				Type tp = new Type();
				tp.setId(rs.getInt("type_id"));
				tp.setName(rs.getString("type_name"));
				goods.setType(tp); // 将 type 对象设置到 goods 对象中
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return goods;
	}

	/**
	 * 通过推荐类型查询商品总数
	 * @param type 推荐类型id
	 * @return 商品总数
	 */
	@Override
	public int countByRecommendType(int type) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT Count(goods.id) FROM goods INNER JOIN recommend ON recommend.goods_id = goods.id INNER JOIN type ON goods.type_id = type.id WHERE recommend.type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	/**
	 * 通过关键字查询商品列表
	 * @param keyword 关键字
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表
	 */
	@Override
	public List<Goods> queryByKeyword(String keyword, int pageNumber, int pageSize) {
		List<Goods> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT goods.id, goods.`name`, goods.cover, goods.image1, goods.image2, goods.price, goods.intro, goods.stock, goods.type_id, type.`name` AS type_name FROM goods INNER JOIN type ON goods.type_id = type.id WHERE goods.`name` LIKE ? LIMIT ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, (pageNumber - 1) * pageSize);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCover(rs.getString("cover"));
				g.setImage1(rs.getString("image1"));
				g.setImage2(rs.getString("image2"));
				g.setPrice(rs.getDouble("price"));
				g.setIntro(rs.getString("intro"));
				g.setStock(rs.getInt("stock"));

				Type t = new Type();
				t.setId(rs.getInt("type_id"));
				t.setName(rs.getString("type_name"));
				g.setType(t);
				goodsList.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return goodsList;
	}

	/**
	 * 通过关键字查询商品总数
	 * @param keyword 关键字
	 * @return 商品总数
	 */
	@Override
	public int countByKeyword(String keyword) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT Count(goods.id) FROM goods INNER JOIN type ON goods.type_id = type.id WHERE goods.`name` LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	/**
	 * 分页查询所有商品
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表
	 */
	@Override
	public List<Goods> queryAll(int pageNumber, int pageSize) {
		List<Goods> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT goods.id, goods.`name`, goods.cover, goods.image1, goods.image2, goods.price, goods.intro, goods.stock, goods.type_id, type.`name` AS type_name FROM goods INNER JOIN type ON goods.type_id = type.id LIMIT ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNumber - 1) * pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCover(rs.getString("cover"));
				g.setImage1(rs.getString("image1"));
				g.setImage2(rs.getString("image2"));
				g.setPrice(rs.getDouble("price"));
				g.setIntro(rs.getString("intro"));
				g.setStock(rs.getInt("stock"));

				Type t = new Type();
				t.setId(rs.getInt("type_id"));
				t.setName(rs.getString("type_name"));
				g.setType(t);
				goodsList.add(g);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.close(rs, pstmt, conn);
		}
		return goodsList;
	}

	/**
	 * 查询所有商品总数
	 * @return 商品总数
	 */
	@Override
	public int countAll() {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT COUNT(goods.id) FROM goods";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	/**
	 * 根据类型id分页查询商品
	 * @param typeid 类型id
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return 商品列表
	 */
	@Override
	public List<Goods> queryByTypeId(int typeid, int pageNumber, int pageSize) {
		List<Goods> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			if (typeid > 0) {
				String sql = "SELECT goods.*, type.`name` AS type_name FROM goods INNER JOIN type ON goods.type_id = type.id WHERE goods.type_id = ? LIMIT ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, typeid);
				pstmt.setInt(2, (pageNumber - 1) * pageSize);
				pstmt.setInt(3, pageSize);
			} else {
				String sql = "SELECT goods.*, type.`name` AS type_name FROM goods INNER JOIN type ON goods.type_id = type.id LIMIT ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, (pageNumber - 1) * pageSize);
				pstmt.setInt(2, pageSize);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods g = new Goods();
				g.setId(rs.getInt("id"));
				g.setName(rs.getString("name"));
				g.setCover(rs.getString("cover"));
				g.setImage1(rs.getString("image1"));
				g.setImage2(rs.getString("image2"));
				g.setPrice(rs.getDouble("price"));
				g.setIntro(rs.getString("intro"));
				g.setStock(rs.getInt("stock"));

				Type t = new Type();
				t.setId(rs.getInt("type_id"));
				t.setName(rs.getString("type_name"));
				g.setType(t);
				goodsList.add(g);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.close(rs, pstmt, conn);
		}
		return goodsList;
	}

	/**
	 * 根据类型id查询商品总数
	 * @param typeId 类型id
	 * @return 商品总数
	 */
	@Override
	public int countByTypeId(int typeId) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT COUNT(goods.id) FROM goods WHERE goods.type_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return count;
	}

	/**
	 * 根据类型id查询商品总数
	 * @param typeid 类型id
	 * @return 商品总数
	 */
	@Override
	public int queryByTypeIdCount(int typeid) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			if (typeid > 0) {
				String sql = "SELECT count(*) FROM goods INNER JOIN type ON goods.type_id = type.id WHERE goods.type_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, typeid);
			} else {
				String sql = "SELECT count(*) FROM goods INNER JOIN type ON goods.type_id = type.id";
				pstmt = conn.prepareStatement(sql);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, pstmt, conn);
		}
		return result;
	}

	void main() {
		GoodsDaoImpl gd = new GoodsDaoImpl();
//		List<Goods> list = gd.queryAllByRecommendId(1);
//		for (Goods g : list) {
//			System.out.println(g);
//		}
		// 测试按类型id查询商品信息
	}
}
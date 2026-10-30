package xyz.lcq12q.dao;

import xyz.lcq12q.bean.Type;

import java.util.List;

public interface ITypeDao {
	//
	int addType(String name);
	int deleteType(int id);
	int updateType(int id, String name);
	// 根据名称查询
	Type findByName(String name);
	// 查询所有类型
	List<Type> findAllType();
	// 根据id查询商品类型
	Type findById(int id);
}

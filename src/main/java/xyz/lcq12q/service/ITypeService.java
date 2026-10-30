package xyz.lcq12q.service;

import xyz.lcq12q.bean.Type;

import java.util.List;

public interface ITypeService {
	//
	int addType(String name);
	int deleteType(int id);
	int updateType(int id, String name);

	List<Type> findAllType();
}

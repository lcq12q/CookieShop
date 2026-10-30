package xyz.lcq12q.service.impl;

import xyz.lcq12q.bean.Type;
import xyz.lcq12q.dao.ITypeDao;
import xyz.lcq12q.dao.impl.TypeDaoImpl;
import xyz.lcq12q.service.ITypeService;

import java.util.List;

public class TypeServiceImpl implements ITypeService {
	private ITypeDao typeDao = new TypeDaoImpl();
	/**
	 * 添加商品类型
	 * @param name
	 * @return result
	 */
	@Override
	public int addType(String name) {
		int result = 0;
		// 检查name是否重复
		Type type = typeDao.findByName(name);
		if (type != null) {
			// 重复，返回-1
			return -1;
		}
		// 不重复，添加类型
		result = typeDao.addType(name);
		return result;
	}

	/**
	 * 删除商品类型
	 * @param id
	 * @return typeDao.deleteType(id)
	 */
	@Override
	public int deleteType(int id) {
		return typeDao.deleteType(id);
	}

	/**
	 * 更新商品类型
	 * @param id
	 * @param name
	 * @return result
	 */
	@Override
	public int updateType(int id, String name) {
		int result = 0;
		// 检查name是否重复
		Type type = typeDao.findByName(name);
		if (type != null && type.getId() != id) {
			// 重复，返回-1
			return -1;
		}
		// 不重复，更新类型
		result = typeDao.updateType(id, name);
		return result;
	}

	/**
	 * 查询所有商品类型
	 * @return types
	 */
	@Override
	public List<Type> findAllType() {
		List<Type> types = typeDao.findAllType();
		return types;
	}

	void main() {
		TypeServiceImpl typeService = new TypeServiceImpl();
		System.out.println(typeService.updateType(15, "sss444"));
	}
}

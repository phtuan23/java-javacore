package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.ConnectionDB;
import entity.Product;

public class ProductDAOImp implements IProduct{
	
	private Connection connection;
	
	public ProductDAOImp() {
		this.connection = ConnectionDB.getConnection();
	}
	@Override
	public boolean addProduct(Product p) {
		CallableStatement callableStatement = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			callableStatement = connection.prepareCall("{CALL spInsertProduct(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, p.getId());
			callableStatement.setNString(2, p.getName());
			callableStatement.setInt(3, p.getStatus());
			callableStatement.setFloat(4, p.getPrice());
			callableStatement.setNString(5, p.getDescription());
			callableStatement.setString(6, sdf.format(p.getExpiration()));
			callableStatement.setString(7, p.getCategory_id());
			if (callableStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean updateProduct(Product p) {
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall("{CALL spUpdateProduct(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, p.getId());
			callableStatement.setString(2, p.getName());
			callableStatement.setInt(3, p.getStatus());
			callableStatement.setFloat(4, p.getPrice());
			callableStatement.setString(5, p.getDescription());
			callableStatement.setDate(6, (java.sql.Date) p.getExpiration());
			callableStatement.setString(7, p.getCategory_id());
			if (callableStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public List<Product> getAll() {
		List<Product> list = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = connection.prepareStatement("select * from product");
			rs = pstm.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				Product p = new Product(id, name, status, price, description, Expiration, category_id);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<Product> getByCat(String catId) {
		List<Product> list = new ArrayList<>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProductByCate(?)}");
			callableStatement.setString(1, catId);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				Product p = new Product(id, name, status, price, description, Expiration, category_id);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public Product detailProduct(String _name) {
		Product p = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProSearchByName(?)}");
			callableStatement.setString(1,_name);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				p = new Product(id, name, status, price, description, Expiration, category_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}

	@Override
	public List<Product> proRemainExp(int day) {
		List<Product> list = new ArrayList<>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProTotalDayExp(?)}");
			callableStatement.setInt(1, day);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				Product p = new Product(id, name, status, price, description, Expiration, category_id);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public Product getById(String _id) {
		Product p = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProById(?)}");
			callableStatement.setString(1, _id);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				p = new Product(id, name, status, price, description, Expiration, category_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}
	
	@Override
	public List<Product> sortByPrice(String type) {
		List<Product> list = new ArrayList<>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProSortByPrice(?)}");
			callableStatement.setString(1, type);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				Product p = new Product(id, name, status, price, description, Expiration, category_id);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public List<Product> sortByName(String type) {
		List<Product> list = new ArrayList<>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProSortByName(?)}");
			callableStatement.setString(1, type);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id"); 
				String name = rs.getString("name");
				int status = rs.getInt("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				Date Expiration = rs.getDate("Expiration");
				String category_id = rs.getString("category_id");
				Product p = new Product(id, name, status, price, description, Expiration, category_id);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public String getCatName(String catId) {
		String catName = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spProGetCateName(?)}");
			callableStatement.setString(1, catId);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				catName = rs.getNString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return catName;
	}
}

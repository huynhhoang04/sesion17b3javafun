import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    private boolean isBookDuplicate(String title, String author) {
        String sql = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addBook(Book book) {
        if (isBookDuplicate(book.getTitle(), book.getAuthor())) {
            System.out.println("Lỗi: Sách này đã tồn tại trong thư viện!");
            return;
        }

        String sql = "INSERT INTO books (title, author, published_year, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setDouble(4, book.getPrice());
            
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("-> Thêm sách thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(int id, Book book) {
        String sql = "UPDATE books SET title=?, author=?, published_year=?, price=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("-> Cập nhật thành công!");
            } else {
                System.out.println("Lỗi: Không tìm thấy sách có ID = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("-> Xóa sách thành công!");
            } else {
                System.out.println("Lỗi: Không tìm thấy sách có ID = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + author + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("published_year"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> listAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("published_year"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager manager = new BookManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ THƯ VIỆN SÁCH ===");
            System.out.println("1. Hiển thị tất cả sách");
            System.out.println("2. Thêm sách mới");
            System.out.println("3. Cập nhật thông tin sách");
            System.out.println("4. Xóa sách");
            System.out.println("5. Tìm sách theo tác giả");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    addBookUI();
                    break;
                case 3:
                    updateBookUI();
                    break;
                case 4:
                    deleteBookUI();
                    break;
                case 5:
                    searchByAuthorUI();
                    break;
                case 0:
                    System.out.println("Kết thúc chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void showAllBooks() {
        List<Book> books = manager.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("Thư viện trống.");
        } else {
            System.out.println("--- Danh sách sách ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    private static void addBookUI() {
        try {
            System.out.print("Nhập tên sách: ");
            String title = scanner.nextLine();
            System.out.print("Nhập tên tác giả: ");
            String author = scanner.nextLine();
            System.out.print("Nhập năm xuất bản: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Nhập giá sách: ");
            double price = Double.parseDouble(scanner.nextLine());

            Book newBook = new Book(title, author, year, price);
            manager.addBook(newBook);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Năm hoặc giá phải là số.");
        }
    }

    private static void updateBookUI() {
        try {
            System.out.print("Nhập ID sách cần sửa: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nhập tên sách mới: ");
            String title = scanner.nextLine();
            System.out.print("Nhập tác giả mới: ");
            String author = scanner.nextLine();
            System.out.print("Nhập năm xuất bản mới: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Nhập giá mới: ");
            double price = Double.parseDouble(scanner.nextLine());

            Book updatedBook = new Book(title, author, year, price);
            manager.updateBook(id, updatedBook);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: ID, năm hoặc giá phải là số.");
        }
    }

    private static void deleteBookUI() {
        try {
            System.out.print("Nhập ID sách cần xóa: ");
            int id = Integer.parseInt(scanner.nextLine());
            manager.deleteBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: ID phải là số nguyên.");
        }
    }

    private static void searchByAuthorUI() {
        System.out.print("Nhập tên tác giả cần tìm: ");
        String author = scanner.nextLine();
        List<Book> results = manager.findBooksByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sách nào của tác giả: " + author);
        } else {
            System.out.println("--- Kết quả tìm kiếm ---");
            for (Book b : results) {
                System.out.println(b);
            }
        }
    }
}

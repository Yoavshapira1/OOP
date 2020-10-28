public class Test {
    public static void main (String[] args){
        Patron patron = new Patron("Yoav", "Shapira", 5, 3, 2,4);
        Book harryPotter = new Book("Harry Potter", "J.K.ROLLING", 1997, 5,4,3);
        Library library = new Library(3,1,3);
        System.out.println("Succeed!");
    }
}

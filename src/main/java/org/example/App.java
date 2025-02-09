package org.example;


import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        do{
            System.out.println("Odaberi:");
            System.out.println("1 za Hibernate");
            System.out.println("2 za JPA");
            System.out.println("0 za izlaz iz porgrama");
            userChoice = scanner.nextInt();
            scanner.nextLine();
            switch (userChoice){
                case 1:
                    hibernateCRUD();
                    break;
                case 2:
                    JPA_CRUD();
                    break;
                default:
                    System.out.println("krivi unos");
            }

        }while(userChoice!= 0);

        if(JPAUtil.getEmf().isOpen()){
            JPAUtil.closeEmf();
        }


        if (HibernateUtil.getSf().isOpen()) {
            HibernateUtil.closeSf();

        }
    }

    public static void hibernateCRUD(){
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        do{
            System.out.println("Odaberite:");
            System.out.println("1 za kriranje narudbe sa proizvodima");
            System.out.println("2 za ispis svih narudbi i proizvoda u njima");
            System.out.println("3 dohvatati narudbu po broju narudbe");
            System.out.println("4 za  brisanje proizvodda iz odredene naudbe");
            System.out.println("5 za dodavanje proizvoda u postojecu narudbu");
            System.out.println("6 za brisanje narudbi i pripadajucih proizvoda");
            System.out.println("0 za izlaz iz HibernateCRUD");

            userChoice = scanner.nextInt();
            scanner.nextLine();
            long orderID;
            long productID;
            String orderNumber;
            switch (userChoice){
                case 1:
                    HibernateCRUD.CreateOrderWithProducts();
                case 2:
                    HibernateCRUD.PrintOrderANDProducts();
                    break;
                case 3:
                    System.out.println("Unesi borj nardbe");
                    orderNumber = scanner.nextLine();
                    ProductOrder order = HibernateCRUD.GetProductOrderWithProducts(orderNumber);
                    HibernateCRUD.printOrderProducts(order);
                    break;
                case 4:
                    System.out.println("Unesi id narudbe u kojoj se proizvod nalazi:");
                    orderID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesi id prozivoda koji zelis izbrisatti");
                    productID = scanner.nextInt();
                    scanner.nextLine();
                    HibernateCRUD.DeleteProductFromOrder(orderID,productID);
                    break;
                case 5:
                    System.out.println("Unesi broj narudbe kojoj zelis dodati proizvod:");
                    orderNumber = scanner.nextLine();
                    System.out.println("Unesi naziv proizvoda koji zelis dodati narubi");
                    String productName = scanner.nextLine();
                    ProductOrder productOrder = HibernateCRUD.GetProductOrderWithProducts(orderNumber);
                    Product product = HibernateCRUD.creteProduct(productName,productOrder);
                    System.out.println(product.getName()+ "dodan u "+ productOrder.getOrederNumber() + " naurudbu");
                    break;
                case 6:
                    System.out.println("Unesi broj narudbe koju zelis izbriaati zajedno sa proizvodima");
                    orderNumber = scanner.nextLine();
                    HibernateCRUD.deleteOrderAndProducts(orderNumber);
                    break;
                default:
                    System.out.println("krivi unos");
            }

        }while (userChoice!=0);

        }
    public static void JPA_CRUD(){
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        do{
            System.out.println("Odaberite:");
            System.out.println("1 za kriranje narudbe sa proizvodima");
            System.out.println("2 za ispis svih narudbi i proizvoda u njima");
            System.out.println("3 dohvatati narudbu po broju narudbe");
            System.out.println("4 za  brisanje proizvodda iz odredene naudbe");
            System.out.println("5 za dodavanje proizvoda u postojecu narudbu");
            System.out.println("6 za brisanje narudbi i pripadajucih proizvoda");
            System.out.println("0 za izlaz iz HibernateCRUD");

            userChoice = scanner.nextInt();
            scanner.nextLine();
            long orderID;
            long productID;
            String orderNumber;
            switch (userChoice){
                case 1:
                    JPACRUD.CreateOrderWithProducts();
                case 2:
                    JPACRUD.PrintOrderANDProducts();
                    break;
                case 3:
                    System.out.println("Unesi borj nardbe");
                    orderNumber = scanner.nextLine();
                    ProductOrder order = JPACRUD.GetProductOrderWithProducts(orderNumber);
                    JPACRUD.printOrderProducts(order);
                    break;
                case 4:
                    System.out.println("Unesi id narudbe u kojoj se proizvod nalazi:");
                    orderID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesi id prozivoda koji zelis izbrisatti");
                    productID = scanner.nextInt();
                    scanner.nextLine();
                    JPACRUD.DeleteProductFromOrder(orderID,productID);
                    break;
                case 5:
                    System.out.println("Unesi broj narudbe kojoj zelis dodati proizvod:");
                    orderNumber = scanner.nextLine();
                    System.out.println("Unesi naziv proizvoda koji zelis dodati narubi");
                    String productName = scanner.nextLine();
                    ProductOrder productOrder = JPACRUD.GetProductOrderWithProducts(orderNumber);
                    Product product = JPACRUD.creteProduct(productName,productOrder);
                    System.out.println(product.getName()+ "dodan u "+ productOrder.getOrederNumber() + " naurudbu");
                    break;
                case 6:
                    System.out.println("Unesi broj narudbe koju zelis izbriaati zajedno sa proizvodima");
                    orderNumber = scanner.nextLine();
                    JPACRUD.deleteOrderAndProducts(orderNumber);
                    break;
                default:
                    System.out.println("krivi unos");
            }

        }while (userChoice!=0);

    }


}






package org.example;


import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int odabir;
        long IDPolaznika, IDProgramaObrazovanja,UpisID,CSVET;


        do {
            System.out.println("Odaberite:");
            System.out.println("1 za usnos novog Polaznika");
            System.out.println("2 za unos programa obrazovanja");
            System.out.println("3 za upis polaznika u program obrazovanja");
            System.out.println("4 za promjenu programa obrazovanja");
            System.out.println("5 za ispis svih polaznika porgrama obrazovanja");
            System.out.println("0 za izlaz");
            odabir = scanner.nextInt();
            scanner.nextLine();

            switch (odabir) {
                case 1:
                    String ime, prezime;
                    System.out.println("Unesite ime polaznika:");
                    ime = scanner.nextLine();
                    System.out.println("Unesite ime polaznika:");
                    prezime = scanner.nextLine();
                    UnosNovogPolaznika(ime,prezime);
                    break;
                case 2:
                    String Naziv;
                    System.out.println("Unestie naziv programa obrazovanja:");
                    Naziv = scanner.nextLine();
                    System.out.println("Unestie CSVET bodove programa obrazovanja:");
                    CSVET = scanner.nextInt();
                    scanner.nextLine();
                    UnosNovogProgramaObrazovanja(Naziv,CSVET);
                    break;
                case 3:

                    System.out.println("Unsi id polaznika kojeg zelis upisti:");
                    IDPolaznika =  scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesi id programa obrazovanja na koji zelis upisati polaznika: ");
                    IDProgramaObrazovanja =  scanner.nextInt();
                    scanner.nextLine();
                    UpisPolaznikaUProgramObrazovanja(IDPolaznika,IDProgramaObrazovanja);
                    break;
                case 4:

                    System.out.println("Uneste id upisa:");
                    UpisID =  scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Uneste id novog program obrazovanja:");
                    IDProgramaObrazovanja =  scanner.nextInt();
                    scanner.nextLine();
                    PrebacivanjePolaznika(IDProgramaObrazovanja,UpisID);
                    break;
                case 5:
                    System.out.println("Unestie id programa obrazovanja");
                    IDProgramaObrazovanja =  scanner.nextInt();
                    scanner.nextLine();
                    ispisPolaznikaProgramObrazovanja(IDProgramaObrazovanja);
                    break;
                default:
                    System.out.println("Krivi unos");


            }


        }while(odabir!=0);



        if (HibernateUtil.getSf().isOpen()) {
            HibernateUtil.closeSf();

        }
    }
    public static void UnosNovogPolaznika(String ime, String prezime){
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            Polaznik polaznik = new Polaznik();
            polaznik.setIme(ime);
            polaznik.setPrezime(prezime);
            session.persist(polaznik);
            transaction.commit();
            System.out.println("Uspjesan unos novog polaznika");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }

        }

    }
    public static void UnosNovogProgramaObrazovanja(String naziv, long CSVET){
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            ProgramObrazovanja programObrazovanja = new ProgramObrazovanja();
            programObrazovanja.setNaziv(naziv);
            programObrazovanja.setCSVET(CSVET);
            session.persist(programObrazovanja);
            transaction.commit();
            System.out.println("Uspjesan unos novog programa obrazovanja");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }

        }

    }
    public static void UpisPolaznikaUProgramObrazovanja(long IDProgramObrazovanja,long IDPolaznik){
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, IDProgramObrazovanja);
            Polaznik polaznik = session.get(Polaznik.class, IDPolaznik);

            Upis upis = new Upis();
            upis.setProgramObrazovanja(program);
            upis.setPolaznik(polaznik);
            session.save(upis);


            transaction.commit();
            System.out.println("Uspjesan upis polaznika u program obrazovanja");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }

        }
    }
    public static void PrebacivanjePolaznika(long IDNovogProgramObrazovanja,long IDUpis){
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, IDNovogProgramObrazovanja);

            Upis upis = session.get(Upis.class,IDUpis);
            upis.setProgramObrazovanja(program);

            session.merge(upis);
            transaction.commit();
            System.out.println("Uspjesan promjena programa obrazovanja");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }

        }
    }
    public static void ispisPolaznikaProgramObrazovanja(long IDProgramObrazovanja){
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, IDProgramObrazovanja);

            for (Upis upis : program.getUpisi()) {
                System.out.println("Ime: "+upis.getPolaznik().getIme()+" Prezime: "+upis.getPolaznik().getPrezime()+ " Program: "+ program.getNaziv()+" CSVET: "+program.getCSVET());

            }


            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            if (session != null) {
                session.close();
            }

        }
    }


}






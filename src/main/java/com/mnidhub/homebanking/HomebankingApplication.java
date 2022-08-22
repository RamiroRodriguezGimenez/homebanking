package com.mnidhub.homebanking;

import com.mnidhub.homebanking.models.*;
import com.mnidhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client client1 = new Client("Melba", "Lorenzo","roman.g@hotmail.com",passwordEncoder.encode("1234"));
			Client client2 = new Client("qwe", "qwe","qwe@gmail.com",passwordEncoder.encode("1234"));
			Client client3 = new Client("admin", "admin","admin@admin.com",passwordEncoder.encode("1234"));

			Account account1 = new Account("VIN001",LocalDateTime.now(),5000.00);
			Account account2 = new Account("VIN002",LocalDateTime.now().plusDays(1),7500.00);

			Account account3 = new Account("VIN003",LocalDateTime.now(),10000.00);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT,1000.00,"Cuota",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,2000.00,"Pago",LocalDateTime.now());

			List<Integer> payments1 = new ArrayList<>(Arrays.asList(12,24,36,48,60));
			Loan loan1 = new Loan("Hipotecario",500.000,payments1);
			List<Integer> payments2 = new ArrayList<>(Arrays.asList(6,12,24));
			Loan loan2 = new Loan("Personal",100.000,payments2);
			List<Integer> payments3 = new ArrayList<>(Arrays.asList(6,12,24,36));
			Loan loan3 = new Loan("Automotriz",300.000,payments3);

			ClientLoan clientLoan1 = new ClientLoan(50000.00,12);
			ClientLoan clientLoan2 = new ClientLoan(10000.00,6);

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT ,CardColor.GOLD, "1234-5678-9123-4567",123, LocalDate.now(), LocalDate.now().plusYears(5));


			System.out.println(client1.toString());
			System.out.println(account1.toString());
			System.out.println(transaction1.toString());
			System.out.println(loan1.toString());

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			client1.addCard(card1);

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			cardRepository.save(card1);
		};
	}

}

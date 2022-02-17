package com.desafioklok.desafioklok;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;

public class TesteAutomatico {

    private static WebDriver driver;

	private static String url;

    @BeforeClass
	public static void configIniciais() {
		
		url = "https://www.amazon.com.br/";
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}

	public String buscaProduto(String ConteudoDaPesquisa){
		driver.get(url);

		driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys(ConteudoDaPesquisa);
		driver.findElement(By.xpath("//*[@id='nav-search-submit-button']")).click();

		String conteudo = "";
		// verifica se o elemento do produto existe, se ele não existir ele procura outro pra verificar se não encontrou
		Boolean estaPresente = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]/div[2]/div[6]/div/div/div/div/div[2]/div[1]/h2/a/span")).size()>0;
		if(estaPresente){
			conteudo = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]/div[2]/div[6]/div/div/div/div/div[2]/div[1]/h2/a/span")).getText();
		}else{
			conteudo = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/span[3]/div[2]/div[1]/div/div/div/div[1]/span[2]")).getText();
		}
		
		return conteudo;
	}

	@Test
	public void buscaProdutoValido(){
		String conteudoPesquisado = "Notebook Lenovo Ultrafino Ideapad";
		assertTrue(buscaProduto(conteudoPesquisado).toUpperCase().contains(conteudoPesquisado.toUpperCase()));
	}

	@Test
	public void buscaProdutoInvalido(){
		String conteudoPesquisado = "YAYrConXNMbhDQqjOwjUOV";
		assertTrue(buscaProduto(conteudoPesquisado).toUpperCase().contains(conteudoPesquisado.toUpperCase()));
	}

	@AfterClass
	public static void FechaBrowser(){
		driver.quit();
	}
}

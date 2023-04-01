package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class US04_Oksana_BookPage_StepDefinition {

    BookPage bookPage;
    LoginPage loginPage;

    @Given("the {string} on the home page")
    public void theOnTheHomePage(String user) {
        loginPage = new LoginPage();
        loginPage.login(user);
        BrowserUtil.waitFor(3);
    }

    @And("the user navigates to {string} page")
    public void theUserNavigatesToPage(String moduleName) {
        bookPage = new BookPage();
        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(1);
    }

    String bookName;
    @When("the user searches for {string} book")
    public void theUserSearchesForBook(String name) {
        bookName = name;
        bookPage = new BookPage();
        bookPage.search.sendKeys(bookName);

    }

    @And("the user clicks edit book button")
    public void theUserClicksEditBookButton() {
        bookPage.editBook(bookName).click();
       // BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();
        BrowserUtil.waitFor(3);

    }

    @Then("book information must match the Database")
    public void bookInformationMustMatchTheDatabase() {
        bookPage= new BookPage();
        String UI_bookName = bookPage.bookName.getAttribute("value");
        String UI_authorName = bookPage.author.getAttribute("value");

        Select select = new Select(bookPage.categoryDropdown);
        String UI_bookCategory = select.getFirstSelectedOption().getText();
        List<String> bookInfoFromUI = new ArrayList<>(Arrays.asList(UI_bookName,UI_authorName,UI_bookCategory));


        String query="select b.name as bookName, author, bc.name as bookCategoryName from books b inner join\n" +
                "   book_categories bc on b.book_category_id = bc.id\n"+
                "   where b.name = '"+bookName+"'";
        DB_Util.runQuery(query);

        List<String> bookInfoListFromDB = DB_Util.getRowDataAsList(1);
        System.out.println("bookInfoListFromDB = " + bookInfoListFromDB);

        String DB_bookName = bookInfoListFromDB.get(0);
        String DB_authorName = bookInfoListFromDB.get(1);
        String BD_categoryName = bookInfoListFromDB.get(2);

        Assert.assertEquals(DB_bookName,UI_bookName);
        Assert.assertEquals(bookInfoListFromDB, bookInfoFromUI);


    }
}

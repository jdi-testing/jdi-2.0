package com.epam.jdi.uitests.testing.unittests.pageobjects.pages;

import com.epam.jdi.uitests.core.interfaces.common.IButton;
import com.epam.jdi.uitests.core.interfaces.common.ILabel;
import com.epam.jdi.uitests.core.interfaces.common.IText;
import com.epam.jdi.uitests.core.interfaces.complex.ICheckList;
import com.epam.jdi.uitests.core.interfaces.complex.IComboBox;
import com.epam.jdi.uitests.core.interfaces.complex.IDropDown;
import com.epam.jdi.uitests.core.interfaces.complex.IDropList;
import com.epam.jdi.uitests.testing.unittests.custom.CheckListOfTypeOne;
import com.epam.jdi.uitests.testing.unittests.enums.ColorsList;
import com.epam.jdi.uitests.testing.unittests.enums.Metals;
import com.epam.jdi.uitests.testing.unittests.enums.Nature;
import com.epam.jdi.uitests.testing.unittests.pageobjects.sections.Summary;
import com.epam.jdi.uitests.web.selenium.elements.base.Element;
import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.complex.CheckList;
import com.epam.jdi.uitests.web.selenium.elements.complex.ComboBox;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.FindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class MetalsColorsPage extends WebPage {

    @FindBy(id = "summary-block")
    public Summary summary;

    @FindBy(id = "calculate-button")
    public Label calculate;

    @FindBy(id = "calculate-button")
    public Button calculateButton;
    @FindBy(id = "calculate-button")
    public ILabel calculateLabel;


    @JDropdown(
            root = @FindBy(css = ".colors"),
            expand = @FindBy(css = ".caret"),
            list = @FindBy(tagName = "li"),
            value = @FindBy(css = ".filter-option")
    ) public IDropDown colorsRootExpandListValue;

    @JDropdown(
            root = @FindBy(css = ".colors"),
            expand = @FindBy(css = ".caret")
    ) public IDropDown colorsRootExpand;

    @JDropdown(
            root = @FindBy(css = ".colors")
    ) public IDropDown colorsRoot;

    @JDropdown(
            root = @FindBy(css = ".colors"),
            value = @FindBy(css = ".filter-option")
    ) public IDropDown colorsRootValue;

    @JDropdown(
            root = @FindBy(css = ".colors"),
            list = @FindBy(tagName = "li")
    ) public IDropDown colorsRootList;

    @JDropdown(
            root = @FindBy(css = ".colors"),
            list = @FindBy(tagName = "li"),
            value = @FindBy(css = ".filter-option")
    ) public IDropDown colorsRootListValue;


    @JDropList(
            root = @FindBy(xpath = ".salad"),
            list = @FindBy(tagName = "li")
    //        jvalue = @JFindBy(tagName = "button")
    ) public IDropList saladDL;

    @FindBy(id = "salad-dropdown")
    public IButton button;


    @JComboBox(
            root = @FindBy(css = ".colors"),
            list = @FindBy(tagName = "li"),
            value = @FindBy(css = ".filter-option"),
            expand = @FindBy(css = ".caret")
    ) public IComboBox jComboBoxRootListValueExpand;

    @JComboBox(
            root = @FindBy(css = ".colors"),
            list = @FindBy(tagName = "li"),
            value = @FindBy(css = ".filter-option")
    ) public IComboBox jComboBoxRootListValue;

    @JComboBox(
        root = @FindBy(css = ".colors"),
        value = @FindBy(css = ".filter-option")
    ) public IComboBox jComboBoxRootList;

    @JDropdown(
        root = @FindBy(css = ".colors"),
        list = @FindBy(tagName = "li"),
        value = @FindBy(css = ".filter-option")
    ) public IDropDown<ColorsList> colors;


    @FindBy(css = ".summ-res")
    public IText calculateText = new Text(){
        @Override
        public String getText() {
         return getDriver().findElement(By.cssSelector(".summ-res")).getText();
        }
    };


    public Text c1alculateText;

    @FindBy(css = "#elements-checklist p")
    public ICheckList<Nature> nature = new CheckList<Nature>() {
        @Override
        public boolean isSelected(WebElement el) {
            return el.findElement(By.tagName("input")).getAttribute("checked") != null;
        }
    };

    public CheckListOfTypeOne natureExtended = new CheckListOfTypeOne("//section[@id='elements-checklist']/p[@class='checkbox']", "/label", "/input");

    @FindBy(xpath = "//*[@id='elements-checklist']//*[label[text()='%s']]/label")
    public ICheckList<Nature> natureTemplate;

    @FindBy(xpath = "//*[@id='elements-checklist']//*[text()='Water']")
    public CheckBox cbWater = new CheckBox() {
        @Override
        public boolean isChecked() {
            Element el = new Element().setLocator(By.xpath("//*[@id='elements-checklist']//*[*[text()='Water']]/input"));
            return el.getInvisibleElement().getAttribute("checked") != null;
        }
    };
    @JComboBox(
        root = @FindBy(css = ".metals"),
        expand = @FindBy(css = ",caret"),
        list = @FindBy(css = "li span"),
        value = @FindBy(css = "input")

    )
    public IComboBox<Metals> comboBox =
            new ComboBox<Metals>() {
                @Override
                public String getText() {
                    Text text = new Text().setLocator(By.cssSelector(".metals .filter-option"));
                    return text.getText();
                }
            };
}



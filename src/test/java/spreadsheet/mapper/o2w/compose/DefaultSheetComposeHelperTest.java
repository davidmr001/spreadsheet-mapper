package spreadsheet.mapper.o2w.compose;

import org.testng.annotations.Test;
import spreadsheet.mapper.AssertUtil;
import spreadsheet.mapper.TestBean;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Sheet;
import spreadsheet.mapper.model.meta.SheetMeta;
import spreadsheet.mapper.o2w.compose.converter.BooleanConverter;
import spreadsheet.mapper.o2w.compose.converter.LocalDateTimeConverter;
import spreadsheet.mapper.o2w.compose.converter.PlainNumberConverter;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by hanwen on 2017/1/5.
 */
public class DefaultSheetComposeHelperTest {

  @Test
  public void testCompose() throws Exception {

    SheetMeta sheetMeta1 = TestFactory.createSheetMeta(true);

    TestBean testBean1 = TestFactory.createBean1();
    TestBean testBean2 = TestFactory.createBean2();

    List<TestBean> data = Arrays.asList(testBean1, testBean2);

    SheetComposeHelper<TestBean> sheetComposeHelper1 = new DefaultSheetComposeHelper<TestBean>().sheetMeta(sheetMeta1).data(data);
    addExtractor(sheetComposeHelper1);

    Sheet sheet1 = sheetComposeHelper1.compose();

    AssertUtil.assertSheetEquals(sheet1, true);

    SheetMeta sheetMeta2 = TestFactory.createSheetMeta(false);

    SheetComposeHelper<TestBean> sheetComposeHelper2 = new DefaultSheetComposeHelper<TestBean>().sheetMeta(sheetMeta2).data(data);
    addExtractor(sheetComposeHelper2);

    Sheet sheet2 = sheetComposeHelper2.compose();

    AssertUtil.assertSheetEquals(sheet2, false);

    SheetComposeHelper<TestBean> sheetComposeHelper3 = new DefaultSheetComposeHelper<TestBean>().sheetMeta(sheetMeta1);

    Sheet sheet3 = sheetComposeHelper3.compose();
    assertEquals(sheet3.sizeOfRows(), 1);
    AssertUtil.assertHeaderRowEquals(sheet3.getRow(1), true);
  }

  private void addExtractor(SheetComposeHelper sheetComposeHelper) {
    sheetComposeHelper.fieldValueConverter(
        new PlainNumberConverter("test.int1"),
        new PlainNumberConverter("test.int2"),
        new PlainNumberConverter("test.long1"),
        new PlainNumberConverter("test.long2"),
        new PlainNumberConverter("test.float1"),
        new PlainNumberConverter("test.float2"),
        new PlainNumberConverter("test.double1"),
        new PlainNumberConverter("test.double2"),
        new BooleanConverter("pass", "failure", "test.boolean1"),
        new BooleanConverter("pass", "failure", "test.boolean2"),
        new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss", "test.localDateTime")
    );
  }
}
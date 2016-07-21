package com.jaouan.viewsfrom;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaouan.viewsfrom.filters.ViewFilter;

import java.util.Comparator;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Tests {@link Views}</a>
 */
public class ViewsTest extends ApplicationTestCase<Application> {

    /**
     * All views.
     * Views hierarchy :
     * RootView
     * |- textView1 (tag: textView_1)
     * |- viewGroup1
     * |   |- textView2 (tag: textView_2)
     * |   |- button1 (invisible)
     * |- viewGroup2
     * |   |- button2
     * |   |- viewGroup3 (gone)
     * |   |   |- button3
     */
    private ViewGroup rootView;
    private ViewGroup viewGroup1;
    private ViewGroup viewGroup2;
    private ViewGroup viewGroup3;
    private View textView1;
    private View textView2;
    private View button1;
    private View button2;
    private View button3;

    public ViewsTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        generateView();
    }

    /**
     * Assert views find.
     *
     * @param views         Views find.
     * @param expectedViews Expected views.
     */
    private void assertViews(final List<View> views, final View... expectedViews) {
        assertEquals("It should have " + expectedViews.length + " views.", expectedViews.length, views.size());

        for (int expectedViewsIndex = 0; expectedViewsIndex < expectedViews.length; expectedViewsIndex++) {
            final View expectedView = expectedViews[expectedViewsIndex];
            assertTrue("It should contain " + expectedView.getClass().getSimpleName() + " at position " + expectedViewsIndex + ".", views.get(expectedViewsIndex) == expectedView);
        }
    }

    @SmallTest
    public void testListAllChildsFromRootView() {
        final List<View> views = Views.from(rootView).find();
        assertViews(views, textView1, viewGroup1, textView2, button1, viewGroup2, button2, viewGroup3, button3);
    }

    @SmallTest
    public void testListAllChildsFromRootViewIncludingRootView() {
        final List<View> views = Views.from(rootView).includingFromViews().find();
        assertViews(views, rootView, textView1, viewGroup1, textView2, button1, viewGroup2, button2, viewGroup3, button3);
    }

    @SmallTest
    public void testListAllChildsFromMultiplesViewGroups() {
        final List<View> views = Views.from(viewGroup1, viewGroup2).find();
        assertViews(views, textView2, button1, button2, viewGroup3, button3);
    }

    @SmallTest
    public void testListFromViewGroupAndFromAnotherViewGroup() {
        final List<View> views = Views.from(viewGroup1).includingFromViews().andFrom(viewGroup2).find();
        assertViews(views, viewGroup1, textView2, button1, button2, viewGroup3, button3);
    }

    @SmallTest
    public void testExcludeView() {
        final List<View> views = Views.from(rootView).excludeView(button3).find();
        assertViews(views, textView1, viewGroup1, textView2, button1, viewGroup2, button2, viewGroup3);
    }

    @SmallTest
    public void testExcludeChildsFromFilteredGroupView() {
        final List<View> views = Views.from(rootView).excludeView(viewGroup3).excludingChildsFromFilteredGroupViews().find();
        assertViews(views, textView1, viewGroup1, textView2, button1, viewGroup2, button2);
    }

    @SmallTest
    public void testFilter() {
        final List<View> views = Views.from(rootView).filteredWith(new ViewFilter() {
            @Override
            public boolean filter(View view) {
                return view == button1;
            }
        }).find();
        assertViews(views, button1);
    }

    @SmallTest
    public void testWithType() {
        final List<View> views = Views.from(rootView).withType(TextView.class, EditText.class).find();
        assertViews(views, textView1, textView2);
    }

    @SmallTest
    public void testWithId() {
        final List<View> views = Views.from(rootView).withId(android.R.id.button1, android.R.id.text1).find();
        assertViews(views, textView1, button1);
    }

    @SmallTest
    public void testWithTag() {
        final List<View> views = Views.from(rootView).withTag("textView_1", "textView_2").find();
        assertViews(views, textView1, textView2);
    }

    @SmallTest
    public void testWithTagRegex() {
        final List<View> views = Views.from(rootView).withTagRegex("textView_[1-9]+", "abc").find();
        assertViews(views, textView1, textView2);
    }

    @SmallTest
    public void testWithVisibility() {
        final List<View> views = Views.from(rootView).withVisibility(View.GONE).find();
        assertViews(views, viewGroup3);
    }

    @SmallTest
    public void testNot() {
        final List<View> views = Views.from(rootView).not().withVisibility(View.GONE).find();
        assertViews(views, textView1, viewGroup1,  textView2, button1, viewGroup2, button2, button3);
    }

    @SmallTest
    public void testCheckParameterIsNotNull() {
        try {
            Views.from(null).find();
        } catch(final IllegalArgumentException illegalArgumentException){
            assertNotNull(illegalArgumentException.getMessage());
        }
    }

    @SmallTest
    public void testOrderedBy() {
        final List<View> views = Views.from(rootView)
                .withTag("textView_1", "textView_2")
                .orderedBy(new Comparator<View>() {
                    @Override
                    public int compare(final View view1, final View view2) {
                        return ((String) view2.getTag()).compareTo((String) view1.getTag());
                    }
                })
                .find();

        assertViews(views, textView2, textView1);
    }

    @MediumTest
    public void testMultipleFilters1() {
        final List<View> views = Views.from(rootView)
                .includingFromViews()
                .not().withId(android.R.id.text1)
                .withVisibility(View.VISIBLE)
                .excludeView(viewGroup3)
                .excludingChildsFromFilteredGroupViews()
                .find();

        assertViews(views, rootView, viewGroup1, textView2, viewGroup2, button2);
    }

    @MediumTest
    public void testMultipleFilters2() {
        final List<View> views = Views.from(viewGroup1)
                .includingFromViews()
                .withVisibility(View.VISIBLE)
                .andFrom(viewGroup2)
                .withId(android.R.id.button2, android.R.id.button3)
                .excludeView(viewGroup3)
                .excludingChildsFromFilteredGroupViews()
                .find();

        assertViews(views, viewGroup1, textView2, button2);
    }

    /**
     * Generate view.
     */
    private void generateView() {
        rootView = new FrameLayout(getContext());

        // TextView in rootView.
        textView1 = new TextView(getContext());
        textView1.setId(android.R.id.text1);
        textView1.setTag("textView_1");
        rootView.addView(textView1);

        // viewGroup1 in rootView.
        viewGroup1 = new LinearLayout(getContext());
        rootView.addView(viewGroup1);

        // TextView in viewGroup1.
        textView2 = new TextView(getContext());
        textView2.setId(android.R.id.text2);
        textView2.setTag("textView_2");
        viewGroup1.addView(textView2);

        // Button in viewGroup1.
        button1 = new Button(getContext());
        button1.setId(android.R.id.button1);
        button1.setVisibility(View.INVISIBLE);
        viewGroup1.addView(button1);

        // viewGroup2 in rootView.
        viewGroup2 = new LinearLayout(getContext());
        rootView.addView(viewGroup2);

        // Button in viewGroup2.
        button2 = new Button(getContext());
        button2.setId(android.R.id.button2);
        viewGroup2.addView(button2);

        // viewGroup3 in viewGroup2.
        viewGroup3 = new LinearLayout(getContext());
        viewGroup3.setVisibility(View.GONE);
        viewGroup2.addView(viewGroup3);

        // Button in viewGroup3, in viewGroup2.
        button3 = new Button(getContext());
        button3.setId(android.R.id.button3);
        viewGroup3.addView(button3);
    }

}
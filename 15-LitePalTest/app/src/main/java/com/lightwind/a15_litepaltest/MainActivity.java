package com.lightwind.a15_litepaltest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.create_database, R.id.add_data, R.id.update_data, R.id.delete_data, R.id
            .query_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_database:
                Connector.getDatabase();
                break;
            case R.id.add_data:
                Book book1 = new Book();
                book1.setName("The Da Vinci Code");
                book1.setAuthor("Dan Brown");
                book1.setPages(454);
                book1.setPrice(16.96);
                book1.setPress("UnKnow");
                book1.save();
                break;
            case R.id.update_data:
                // 更新数据库方式一：
                Book book2 = new Book();
                book2.setName("The Lost Symbol");
                book2.setAuthor("Dan Brown");
                book2.setPages(510);
                book2.setPrice(19.95);
                book2.setPress("UnKnow");
                book2.save();
                book2.setPrice(10.96);
                book2.save();

                // 更新数据库方式二：
                Book book3 = new Book();
                book3.setPrice(14.95);
                book3.setPress("Anchor");
                book3.updateAll("name = ? and author = ?", "The Lost Symbol", "Dan Brown");

                // 更新成默认值
                Book book4 = new Book();
                book4.setToDefault("pages");
                book4.updateAll();
                break;
            case R.id.delete_data:
                // 删除数据
                DataSupport.deleteAll(Book.class, "price < ?", "15");
                break;
            case R.id.query_data:
                List<Book> bookList = DataSupport.findAll(Book.class);
                for (Book book : bookList) {
                    Log.d("MainActivity", "Name:" + book.getName());
                    Log.d("MainActivity", "Press:" + book.getPress());
                    Log.d("MainActivity", "Pages:" + book.getPages());
                    Log.d("MainActivity", "Author:" + book.getAuthor());
                    Log.d("MainActivity", "Price:" + book.getPrice());
                }

                // 查询第一条数据
//                Book firstBook = DataSupport.findFirst(Book.class);

                // 查询最后一条数据
//                Book lastBook = DataSupport.findLast(Book.class);

                // 只查询name和author两列数据
//                List<Book> books = DataSupport.select("name", "author").find(Book.class);

                // 只查询页数大于400的数据
//                List<Book> books = DataSupport.where("pages > ?", "400").find(Book.class);

                // 将查询结果按照书价从高到低排序
//                List<Book> books = DataSupport.order("price desc").find(Book.class);

                // 只查询前3条数据
//                List<Book> books = DataSupport.limit(3).find(Book.class);

                // 查询表中的第2条、第3条、第4条数据
//                List<Book> books = DataSupport.limit(3).offset(1).find(Book.class);

//                List<Book> books = DataSupport.select("name", "author", "pages")
//                        .where("pages > ?", "400")
//                        .order("pages")
//                        .limit(10)
//                        .offset(10)
//                        .find(Book.class);

                Cursor cursor = DataSupport.findBySQL("select * from Book where pages > ? and " +
                        "price < ?", "400", "15");
                break;
        }
    }
}

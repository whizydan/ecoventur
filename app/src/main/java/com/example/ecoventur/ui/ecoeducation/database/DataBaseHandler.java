package com.example.ecoventur.ui.ecoeducation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ecoventur.ui.ecoeducation.models.Articles;
import com.example.ecoventur.ui.ecoeducation.models.Quiz;
import com.example.ecoventur.ui.ecoeducation.models.QuizCategories;
import com.example.ecoventur.ui.ecoeducation.models.Tips;

import java.util.ArrayList;
import java.util.Calendar;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ecoventur"; // Set your database name
    private static final int DATABASE_VERSION = 1; // Set your database version

    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tipsTable = "CREATE TABLE \"tips\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"url\"\tTEXT,\n" +
                "\t\"tip\"\tTEXT,\n" +
                "\t\"shown\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")";
        String contentTable = "CREATE TABLE \"content\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"type\"\tTEXT,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"image\"\tTEXT,\n" +
                "\t\"content\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")";
        String quizTable = "CREATE TABLE \"quiz\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"question\"\tTEXT,\n" +
                "\t\"a\"\tTEXT,\n" +
                "\t\"b\"\tTEXT,\n" +
                "\t\"c\"\tTEXT,\n" +
                "\t\"d\"\tTEXT,\n" +
                "\t\"answer\"\tTEXT,\n" +
                "\t\"category\"\tTEXT,\n" +
                "\t\"completed\"\tTEXT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")";
        db.execSQL(tipsTable);
        db.execSQL(contentTable);
        db.execSQL(quizTable);
    }
    public void updateProgress(String id){
        Calendar calendar = Calendar.getInstance();
        int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update quiz set completed = '"+weekNumber+"' where id='"+id+"'");
    }
    public ArrayList<Quiz> getQuizes(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from quiz where category = '"+category+"'";
        ArrayList<Quiz> contentList = new ArrayList<>();
        Cursor queryCursor = db.rawQuery(query,null);
        while(queryCursor.moveToNext()){
            Quiz content = new Quiz();
            content.setId(queryCursor.getString(0));
            content.setQuestion(queryCursor.getString(1));
            content.setA(queryCursor.getString(2));
            content.setB(queryCursor.getString(3));
            content.setC(queryCursor.getString(4));
            content.setD(queryCursor.getString(5));
            content.setAnswer(queryCursor.getString(6));
            content.setCategory(queryCursor.getString(7));

            contentList.add(content);
        }
        queryCursor.close();
        return contentList;
    }
    public Tips getTip(){
        SQLiteDatabase db = this.getWritableDatabase();
        Tips tip = new Tips();
        String query = "select * from tips where shown = 'no'";
        Cursor queryCursor = db.rawQuery(query,null);
        while(queryCursor.moveToNext()){
            tip.setTip(queryCursor.getString(2));
            tip.setId(queryCursor.getString(0));
            tip.setUrl(queryCursor.getString(1));
        }
        queryCursor.close();
        return tip;
    }
    public ArrayList<Articles> getArticles(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from content where type = 'article'";
        ArrayList<Articles> contentList = new ArrayList<>();
        Cursor queryCursor = db.rawQuery(query,null);
        while(queryCursor.moveToNext()){
            Articles content = new Articles();
            content.setId(queryCursor.getString(0));
            content.setType(queryCursor.getString(1));
            content.setTitle(queryCursor.getString(2));
            content.setImage(queryCursor.getString(3));
            content.setUrl(queryCursor.getString(4));

            contentList.add(content);
        }
        queryCursor.close();
        return contentList;
    }
    public ArrayList<Articles> getTutorials(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from content where type = 'tutorial'";
        ArrayList<Articles> contentList = new ArrayList<>();
        Cursor queryCursor = db.rawQuery(query,null);
        while(queryCursor.moveToNext()){
            Articles content = new Articles();
            content.setId(queryCursor.getString(0));
            content.setType(queryCursor.getString(1));
            content.setTitle(queryCursor.getString(2));
            content.setImage(queryCursor.getString(3));
            content.setUrl(queryCursor.getString(4));

            contentList.add(content);
        }
        queryCursor.close();
        return contentList;
    }
    public void addTips(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String[] queries = {
        "insert into tips(url,tip,shown) values('https://forestnation.com/blog/10-easy-ways-for-students-to-participate-in-reforestation-efforts/','Participate in tree-planting activities to contribute to reforestation efforts.','no')",
        "insert into tips(url,tip,shown) values('https://www.mackay.qld.gov.au/residents/environment/environmental-sustainability/sustainability_living_guide/products/avoid_products_with_excessive_packaging','Avoid buying products with excessive packaging.','no')",
        "insert into tips(url,tip,shown) values('https://twin-cities.umn.edu/news-events/how-stay-warm-while-keeping-thermostat-low#:~:text=In%20order%20to%20stay%20warm,will%20clothing%20with%20closed%20necklines.','Turn down the thermostat and wear layers to stay warm in winter.','no')",
        "insert into tips(url,tip,shown) values('https://www.un.org/en/climatechange/raising-ambition/renewable-energy-transition','Support initiatives that promote renewable energy sources','no')",
        "insert into tips(url,tip,shown) values('https://content.ces.ncsu.edu/before-you-recycle-choose-to-reuse#:~:text=The%20second%20%E2%80%9CR%2C%E2%80%9D%20reuse,they%20are%20actually%20reusing%20it.','Repair items instead of discarding them to reduce waste','no')",
        "insert into tips(url,tip,shown) values('https://www.nps.gov/articles/leave-no-trace-seven-principles.htm','Practice responsible camping and hiking, leaving no trace.','no')",
        "insert into tips(url,tip,shown) values('https://iburucoffee.com/blogs/blog/why-should-we-use-reusable-coffee-cups#:~:text=Choose%20reusable%20travel%20cups%20made,from%20500%20to%203000%20times.','Choose a reusable coffee cup instead of disposable cups.','no')",
        "insert into tips(url,tip,shown) values('https://www.awardaroo.io/short-reads/5-benefits-of-using-eco-friendly-cleaning-products#:~:text=Eco%2Dfriendly%20products%20promote%20renewable,right%20solution%20for%20climate%20change.','Use eco-friendly cleaning products to reduce chemical pollution.','no')",
        "insert into tips(url,tip,shown) values('https://sustainability.yale.edu/blog/how-sustainably-dispose-your-technological-waste','Dispose of electronic waste responsibly through recycling programs.','no')",
        "insert into tips(url,tip,shown) values('https://19january2017snapshot.epa.gov/www3/watersense/kids/tap-off.html#:~:text=Just%20by%20turning%20off%20the,true%20when%20you%20wash%20dishes.','Turn off faucets while brushing teeth or washing hands.','no')",
        "insert into tips(url,tip,shown) values('https://oceanconservancy.org/blog/2012/08/31/five-reasons-to-use-cloth-napkins-over-paper/#:~:text=Cloth%20napkins%20are%20more%20durable.&text=You%20can%20if%20you%20used,going%20through%20fewer%20paper%20products.','Use a cloth napkin instead of disposable paper napkins.','no')",
        "insert into tips(url,tip,shown) values('https://earth.org/environmental-education/#:~:text=Living%20a%20sustainable%20lifestyle%20at,friendly%20habits%20in%20the%20home.','Educate others about the importance of ecological sustainability.','no')",
        "insert into tips(url,tip,shown) values('https://www.earthday.org/actions/green-up-with-a-cleanup-volunteer-for-a-cleanup-in-your-community/','Participate in community clean-up events.','no')",
        "insert into tips(url,tip,shown) values('https://www.energy.gov/energysaver/when-turn-your-lights#:~:text=Since%20they%20are%20already%20very,15%20minutes%2C%20turn%20it%20off.','Turn off lights when leaving a room to conserve energy.','no')",
        "insert into tips(url,tip,shown) values('https://courses.lumenlearning.com/suny-monroe-environmentalbiology/chapter/15-2-waste-management-strategies/#:~:text=Recycling%20refers%20to%20recovery%20of,for%20identical%20applications%20is%20reduced.','Recycle paper, glass, and metal to minimize landfill waste.','no')",
        "insert into tips(url,tip,shown) values('https://repositorio.ul.pt/bitstream/10451/39267/1/ICS_JGraca_MTruninger_Reducing_postprint.pdf','Reduce meat consumption and explore plant-based meals.','no')",
        "insert into tips(url,tip,shown) values('https://greenbusinessbureau.com/green-practices/products/packaging/8-eco-friendly-packaging-alternatives-for-your-businesss-shipping-needs/','Choose products with minimal packaging or eco-friendly packaging.','no')",
        "insert into tips(url,tip,shown) values('https://www.ridetogetherpierce.com/420/Benefits#:~:text=Environmental%20benefits,emissions%20and%20improve%20air%20quality.','Use public transportation, carpool, or bike instead of driving alone.','no')",
        "insert into tips(url,tip,shown) values('https://brinly.com/brinlyu/using-native-plants-for-local-ecosystem-support/#:~:text=Native%20plants%2C%20including%20trees%2C%20shrubs,local%20ecosystems%20and%20reduce%20biodiversity.','Plant native trees and plants to support local ecosystems.','no')",
        "insert into tips(url,tip,shown) values('https://www.homebiogas.com/blog/kitchen-waste-composting/#:~:text=Kitchen%20waste%20composting%20can%20help,scraps%20into%20valuable%20soil%20gold.','Compost kitchen scraps to reduce landfill waste.','no')",
        "insert into tips(url,tip,shown) values('https://www.constellation.com/energy-101/water-conservation-tips0.html','Conserve water by fixing leaks and using water-saving appliances.','no')"
        };

        for (String query : queries) {
            db.execSQL(query);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public void addContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String[] content = {
                "insert into content(type,title,image,content) values('article','Participate in tree-planting activities to contribute to reforestation efforts.','https://static.wixstatic.com/media/11062b_8a25f8d7e46a43a3b56c455904fdf053~mv2.jpeg/v1/fill/w_1000,h_664,al_c,q_85,usm_0.66_1.00_0.01/11062b_8a25f8d7e46a43a3b56c455904fdf053~mv2.jpeg','https://forestnation.com/blog/10-easy-ways-for-students-to-participate-in-reforestation-efforts/')",
                "insert into content(type,title,image,content) values('article','Support initiatives that promote renewable energy sources.','https://fundsforngosmedia.s3.amazonaws.com/wp-content/uploads/2022/09/21063952/SDG6-min.jpeg','https://www.un.org/en/climatechange/raising-ambition/renewable-energy-transition')",
                "insert into content(type,title,image,content) values('article','Use a cloth napkin instead of disposable paper napkins.','https://i0.wp.com/theguiltygranola.com/wp-content/uploads/2021/02/pexels-photo-1395967-2333559700-1648943124780.jpeg?fit=867%2C650&ssl=1','https://oceanconservancy.org/blog/2012/08/31/five-reasons-to-use-cloth-napkins-over-paper/#:~:text=Cloth%20napkins%20are%20more%20durable.&text=You%20can%20if%20you%20used,going%20through%20fewer%20paper%20products.')",
                "insert into content(type,title,image,content) values('article','Compost kitchen scraps to reduce landfill waste.','https://lovefoodhatewaste.co.nz/wp-content/uploads/2018/05/Compost.jpg','https://www.homebiogas.com/blog/kitchen-waste-composting/#:~:text=Kitchen%20waste%20composting%20can%20help,scraps%20into%20valuable%20soil%20gold.')",
                "insert into content(type,title,image,content) values('tutorial','What is sustainable living','https://miro.medium.com/v2/resize:fit:1200/1*0ZuNG5BG5kVYl3621CpE3w.jpeg','https://www.youtube.com/watch?v=upNUbVmBQeI&ab_channel=Howcast')",
                "insert into content(type,title,image,content) values('tutorial','Shave off the waste','https://indiacsr.in/wp-content/uploads/2023/05/Zero-Waste-Lifestyle.png','https://www.youtube.com/watch?v=VzC6w0DdEtA&ab_channel=ExploringAlternatives')",
                "insert into content(type,title,image,content) values('tutorial','What is sustainability','https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.activesustainability.com%2Fsustainable-development%2Fwhat-is-sustainability%2F&psig=AOvVaw2xCvh3vUPRv2vgH7f7r3LE&ust=1702728684704000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCMCDneS0kYMDFQAAAAAdAAAAABAH','https://www.youtube.com/watch?v=kZIrIQDf1nQ&ab_channel=ACCIONA')",
                "insert into content(type,title,image,content) values('tutorial','How to do ecosystem restoration','https://www.rekoforest.org/wp-content/uploads/2018/06/RESTORASI-EKOSISTEM-RIAU-RER-Ecosystem-Restoration.png','https://www.youtube.com/watch?v=XhjN8Xux2I4&ab_channel=UNEnvironmentProgramme')"
        };

        for (String query : content) {
            db.execSQL(query);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public void addQuizes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        //question category 1
        String[] insertCategory1 = {
                "insert into quiz(question,a,b,c,d,answer,category) values('Where is waste thrown','Bins','In the open','toilet','burning','a','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the primary goal of waste management?','Leisure','None','Recycling.','Soft life','c','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values(' What is the process of breaking down organic waste into nutrient-rich soil?','Compositing','Petrification','Growth','development','a','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What type of waste poses a threat to human health and the environment?','Bio-degradable','Hazardous.','toilet','Nitrogen','b','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for reducing waste at its source by using less and reusing items?','Recycling','Reusing','Remaking','Source Reduction.','d','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for waste that cannot be recycled or easily decomposed?','non-rottable','Bio-degradable','Non-biodegradable.','rottable','c','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which step in the waste management hierarchy is prioritized over recycling?','Reuse','Reduce','Burn','Bury','a','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the collection and reuse of materials that would otherwise be thrown away?','Salvage','Remake','Collect','Freebies','a','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the primary purpose of a landfill?','Leftover from activity','Bury items','Collect rainwater','Disposal','d','Waste Management')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What process involves converting waste materials into reusable materials, reducing the need for raw resource extraction?','Decoupling',' Upcycling.','Salvaging','Recycling','b','Waste Management')"
        };
        for (String query : insertCategory1) {
            db.execSQL(query);
        }

        //question category 2
        String[] insertCategory2 = {
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the process of breaking down materials to create new products?','Decoupling','Recycling','Salvaging','Upcycling','b','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term describes the reduction of waste generation through the design and manufacturing of products?','Upcycling','Zero-waste','Decoupling','Eco-design','d','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the practice of using items again in their current form without processing?','Recycling','Upcycling','Reusing','Salvaging','c','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term refers to the sustainable management of forests to meet current needs without compromising future generations?','Sustainable forestry','Afforestation','Reforestation','Deforestation','a','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the process of converting waste materials into reusable materials?','Recycling','Upcycling','Decoupling','Salvaging','a','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the process of converting waste materials into reusable materials?','Recycling','Upcycling','Decoupling','Salvaging','a','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which practice involves capturing rainwater for later use in irrigation or other purposes?','Water harvesting','Desalination','Water recycling','Aquaponics','a','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for generating energy from naturally replenishing resources?','Renewable','Sustainable','Clean','Green','a','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term describes the practice of reducing energy consumption by using energy-efficient technologies?','Green energy','Energy conservation','Decoupling','Sustainable energy','b','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for creating products with minimal environmental impact throughout their lifecycle?','Decoupling','Eco-friendly','Sustainable','Greenwashing','c','Power');",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term refers to the process of restoring degraded ecosystems to their original state?','Reforestation','Conservation','Restoration','Decoupling','c','Power');"

        };
        for (String query : insertCategory2) {
            db.execSQL(query);
        }
        //question category 3
        String[] insertcategory3 = {
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for reducing waste by using materials for as long as possible?','Decoupling','Reuse','Salvaging','Recycling','b','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for reducing waste by using materials for as long as possible?','Decoupling','Reuse','Salvaging','Recycling','b','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the practice of using natural resources in a way that preserves the ecosystem?','Ecofriendly','Renewable','Sustainable','Organic','c','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term refers to the responsible management of resources to meet current needs without compromising future generations?','Sustainability','Greenwashing','Degradation','Conservation','a','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the process of converting waste materials into reusable materials?','Repurposing','Landfilling','Upcycling','Wasting','c','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the impact of excessive noise pollution on human health?','Deafness','Insomnia','Tinnitus','Hypertension','d','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the practice of growing food without the use of synthetic pesticides and fertilizers?','Organic','GMO','Industrial','Conventional','a','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term describes the use of renewable energy sources like wind and solar power?','FossilFuel','GreenEnergy','Nonrenewable','Traditional','b','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the deliberate modification of an organisms genetic material?','Mutation','SelectiveBreeding','GeneticEngineering','NaturalSelection','c','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Which term refers to the process of capturing and storing carbon dioxide to mitigate climate change?','CarbonSequestration','CarbonFootprint','CarbonEmission','CarbonNeutral','a','Sustainability')",
                "insert into quiz(question,a,b,c,d,answer,category) values('What is the term for the practice of using transportation methods that minimize environmental impact?','SustainableTransport','ConventionalTransport','EcoTransport','FuelEfficient','a','Sustainability')",

        };
        for (String query : insertcategory3) {
            db.execSQL(query);
        }
        //question category 4
        String[] insertCategory4 = {
                "insert into quiz(question,a,b,c,d,answer,category) values('Is a car durable and built to last?','Yes','No','Maybe','NotSure','a','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Does flour use recycled materials?','Yes','No','Sometimes','NotSure','b','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Is the packaging in gifts minimal or eco-friendly?','Yes','No','Somewhat','NotSure','b','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Is Silk made from sustainable materials?','Yes','No','Partially','NotSure','b','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Are phones produced ethically and under fair labor practices?','Yes','No','NotSure','N/A','b','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Can shoes be easily repaired or refurbished?','Yes','No','Maybe','NotSure','a','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Is a bicycle energy-efficient in its usage?','Yes','No','NotSure','N/A','a','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Do birds eat drops off food on hot metal?','Yes','No','Partially','NotSure','a','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Is a diesel tractor designed to have a low environmental impact?','Yes','No','Somewhat','NotSure','b','Think twice shopping')",
                "insert into quiz(question,a,b,c,d,answer,category) values('Can most products be easily recycled at the end of their life?','Yes','No','Maybe','NotSure','b','Think twice shopping')"

        };
        for (String query : insertCategory4) {
            db.execSQL(query);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public ArrayList<QuizCategories> getQuizCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor queryCursor = db.rawQuery("select distinct category from quiz",null);
        ArrayList<QuizCategories> quizes = new ArrayList<>();
        while(queryCursor.moveToNext()){
            QuizCategories quiz = new QuizCategories();
            quiz.setName(queryCursor.getString(0));

            quizes.add(quiz);
        }
        queryCursor.close();
        return quizes;
    }
    public void updateTip(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update tips set shown = 'yes' where id = '"+id+"'";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

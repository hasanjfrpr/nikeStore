package com.example.digikala.data.repo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.digikala.data.repo.Product
import com.example.digikala.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract  fun  getDao() : ProductLocalDataSource

}
package com.tq.myapplication.database
import com.tq.abc.Repository

class BarRepository private constructor(private val dao: BarDao) 
: Repository<Bar>{

    override fun readAll() = dao.queryAll()
    override fun read(id: Long) = dao.query(id)
    override fun write(entity: Bar) = dao.insert(entity)
    override fun erase(entity: Bar) = dao.delete(entity)

    companion object {
        private var instance: BarRepository? = null
        fun get(dao: BarDao): BarRepository {
            return instance ?: synchronized(this) {
                instance ?: BarRepository(dao)
            }
        }
    }
}

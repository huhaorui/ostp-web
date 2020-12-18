package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Course
import top.ostp.web.model.Major

@Mapper
@Repository
interface CourseMapper {

    @Insert("insert into course (id, major, name) values (#{id}, #{major.id}, #{name});")
    fun insert(course: Course): Int


    @Delete("delete from course where id = #{id}")
    fun delete(course: Course): Int


    @Update("update course set name = #{name} where id = #{id}")
    fun update(course: Course): Int

    @Select("select * from course")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Course::class)
    fun selectAll(): List<Course>

    @Select("select * from course where id = #{id} limit 1")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    fun selectById(id: String): Course?


    @Select("select * from course where name = #{name}")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Course::class)
    fun selectByName(name: String): List<Course>

    @Select("select * from course where major = #{id}")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Course::class)
    fun selectByMajor(major: Major): List<Course>

}
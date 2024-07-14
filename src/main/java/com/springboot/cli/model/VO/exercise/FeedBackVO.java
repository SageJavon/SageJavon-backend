package com.springboot.cli.model.VO.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackVO {
    private Integer score;
    private String correctAnswer;
    private String suggestion="学习建议：\\n\" +\n" +
            "        \"理解基础概念：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"确保理解变量的概念，如何声明和使用。\\n\" +\n" +
            "        \"理解赋值语句和表达式的执行顺序。\\n\" +\n" +
            "        \"掌握基本语法：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"熟悉 Java 中的数据类型（如 int）、运算符和基本语句（如 if、for、while）。\\n\" +\n" +
            "        \"学习调试技巧：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"学会使用断点和调试器来逐步执行代码，观察变量的变化和程序的执行流程，这样可以更清晰地理解代码的运行过程。\\n\" +\n" +
            "        \"加强对控制流的理解：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"练习使用条件语句（if-else）、循环语句（for、while）等，控制程序的流程和逻辑。\\n\" +\n" +
            "        \"尝试更复杂的示例和项目：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"通过解决问题或实现小项目来应用所学知识，这有助于加深理解和掌握编程技能。\\n\" +\n" +
            "        \"学习资源推荐：\\n\" +\n" +
            "        \"网上课程和教程：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"Coursera 和 edX 等平台上有很多优秀的计算机科学课程，可以免费学习或付费学习。\\n\" +\n" +
            "        \"Oracle 官方文档：Java 编程语言的官方文档提供了详尽的学习资料和示例。\\n\" +\n" +
            "        \"书籍：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"《Thinking in Java》 by Bruce Eckel：这本书被认为是入门 Java 编程的经典之作，适合初学者阅读。\\n\" +\n" +
            "        \"《Effective Java》 by Joshua Bloch：虽然更适合有一定经验的开发人员，但对理解 Java 语言的特性和最佳实践也非常有帮助。\\n\" +\n" +
            "        \"在线资源：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"Stack Overflow：一个社区驱动的问答网站，可以在这里找到关于 Java 编程的各种问题和解答。\\n\" +\n" +
            "        \"GitHub：浏览开源项目的代码库，了解其他开发者是如何使用 Java 的。\\n\" +\n" +
            "        \"练习平台：\\n\" +\n" +
            "        \"\\n\" +\n" +
            "        \"LeetCode、HackerRank 等在线编程练习平台提供了大量的编程题目和挑战，适合练习算法和数据结构的应用。\\n\" +\n" +
            "        \"总结：\\n\" +\n" +
            "        \"通过深入学习和练习，结合有效的学习资源和平台，这位学生可以逐步提升他的 Java 编程技能。重要的是保持耐心和持续的学习态度，编程技能是通过不断练习和实践来提升的！";
}

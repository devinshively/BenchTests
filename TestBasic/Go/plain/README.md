Spent ~6 hours 10.05.14 getting the project setup w/ postgres integration. The hard part was trying to find an elegant 
way to handle routing. I don't like having to define my routes separate from where they are implemented. I am 
happy with the solution. 

REFLECTION:

I like the language, it is easy to write. I did find the documentation good but the tutorials for how to structure
multi file projects and multiple modules was lacking. I could definitely whip up a quick one file rest service
 fairly easy. I am fine using SQL but having to scan for each field of a struct manually is a pain, I will look
 into sqlx in the future. I am not quite sure I see any benefit over Java. Maybe speed? Parallelism and 
 concurrency are easy to setup and I like how it compiles to a single binary. Type inference is nice. Run with
 gin for live code reload.
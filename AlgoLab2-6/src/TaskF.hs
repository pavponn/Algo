-- )))))))))))))) Я правда хотел написать преобразование Фурье, но что-то не сложилось
module TaskF where
 
import Data.List 
import System.IO
 
   
main = do
  input1 <- getLine
  input2 <- getLine 
  let x = (read input1 :: Integer)
  let y = (read input2 :: Integer)
  let res = x * y
  putStrLn $ show res
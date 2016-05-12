import Test.QuickCheck
import Data.List (splitAt)
import Data.Char (intToDigit, digitToInt)

prop_m :: Integer -> Property
prop_m n = n > 0 ==> (nextPalin n) == (nextPalin2 n)

nextPalin :: Integer -> Integer
nextPalin number =
  head $ dropWhile (not . isPalindrome . show) [number+1..]
  where
    isPalindrome list = reverse list == list

nextPalin2 :: Integer -> Integer
nextPalin2 number
  | (length charList) `mod` 2 == 0 =  read ((reverse evenP) ++ evenP)
  | otherwise = read ((reverse oddP) ++ tail oddP)
  where
    charList = show (number + 1)
    evenP = map intToDigit (evenPalin charList)
    oddP = map intToDigit (oddPalin charList)

evenPalin :: String -> [Int]
evenPalin charList =
  compareParts fp sp False []
  where
    (firstPart, secondPart) = splitAt ((length charList)`div`2) charList
    fp = map digitToInt (reverse firstPart)
    sp = map digitToInt secondPart

oddPalin :: String -> [Int]
oddPalin charList =
  compareParts (tail fp) sp False [head fp]
  where
    (firstPart, secondPart) = splitAt (((length charList)`div`2) + 1) charList
    fp = map digitToInt (reverse firstPart)
    sp = map digitToInt secondPart

compareParts :: [Int] -> [Int] -> Bool -> [Int] -> [Int]
compareParts [] _ _ res = res
compareParts (fh:ft) (sh:st) inc res
  | fh < sh && not inc = compareParts ft st True ((tryIncrease (res ++ [fh]) []))
  | otherwise = compareParts ft st inc (res ++ [fh])

tryIncrease :: [Int] -> [Int] -> [Int]
tryIncrease (h:t) res
  | h < 9 = res ++ ((h + 1) : t)
  | otherwise = tryIncrease t (res ++ [h])

import Data.List (isPrefixOf)
import Loc (newLoc)

main :: IO ()
main = do
    file <- readFile "data/createTest.txt"
    let realLines = filter removeComments $ lines file
    let answers = map analyzeLine $ realLines
    report answers
    -- putStrLn $ show ls

data Answer = Passed | Failed String deriving (Show)

removeComments :: String -> Bool
removeComments line =
    not $ "#" `isPrefixOf` line

analyzeLine :: String -> Answer
analyzeLine line =
    let pieces = splitOn ',' line
        x = read (pieces !! 0) :: Int
        y = read (pieces !! 1) :: Int
        z = read (pieces !! 2) :: Int
        sx = read (pieces !! 3) :: Int
        sy = read (pieces !! 4) :: Int
        sz = read (pieces !! 5) :: Int
        shouldWork = (pieces !! 6) == "true"
        rawLoc = newLoc x y z
        solvedLoc = newLoc sx sy sz
        works = rawLoc == solvedLoc in
    if works == shouldWork then
        Passed
    else
        Failed $ (show rawLoc) ++ " " ++ (show solvedLoc)

report :: [Answer] -> IO ()
report answers = do
    let numTested = length answers
    let numCorrect = length $ filter isSinglePass answers
    putStrLn "Results:"
    putStrLn "debug: 1"
    putStrLn $ if (foldr isPass True answers) then "Success!" else "Failure!"
    putStrLn $ (show numCorrect) ++ "/" ++ (show numTested) ++ " succeeded"
    putStrLn ""
    mapM_ reportSingle answers

reportSingle :: Answer -> IO ()
reportSingle Passed = return ()
reportSingle (Failed message) =
    putStrLn message

isSinglePass :: Answer -> Bool
isSinglePass Passed = True
isSinglePass _ = False

isPass :: Answer -> Bool -> Bool
isPass Passed True = True
isPass _ _ = False

splitOn delimiter =
    foldr f [[]]
    where f c l@(x:xs) | c == delimiter = []:l
                       | otherwise = (c:x):xs

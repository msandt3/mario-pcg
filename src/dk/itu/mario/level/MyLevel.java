package dk.itu.mario.level;

import java.util.Random;

import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.engine.sprites.Enemy;


public class MyLevel extends Level{
	//Store information about the level
	 public   int ENEMIES = 0; //the number of enemies the level contains
	 public   int BLOCKS_EMPTY = 0; // the number of empty blocks
	 public   int BLOCKS_COINS = 0; // the number of coin blocks
	 public   int BLOCKS_POWER = 0; // the number of power blocks
	 public   int COINS = 0; //These are the coins in boxes that Mario collect


	 //set skill initially to zero to update after first run
	 private static enum Skill {EXCELLENT,GOOD,AVERAGE,BAD};
	 //player types - three for now possible to add more later
	 private static boolean isKiller = false, isExplorer = false, isRunner=false;

	 //explore value
	 private static int exploreVal = 0;
	 private static double killerVal = 0;

 
		private static Random levelSeedRandom = new Random();
	    public static long lastSeed;

	    Random random;

  
	    private int difficulty;
	    private int type;
		private int gaps;
		private GamePlay playerMetrics;
		private Skill playerSkill;
		
		public MyLevel(int width, int height)
	    {
			super(width, height);
	    }


		public MyLevel(int width, int height, long seed, int difficulty, int type, GamePlay playerMetrics)
	    {
	        this(width, height);
	        this.playerMetrics = playerMetrics;
	        this.playerSkill = Skill.AVERAGE;
	        creat(seed, difficulty, type);
	    }

	    public void creat(long seed, int difficulty, int type)
	    {
	        //now lets evaluate the player
	        this.difficulty = playerMetrics.difficulty;
	        evaluatePlayer();
	        System.out.println("Player skill is - "+playerSkill);
	        

	        this.type = type;
	        

	        lastSeed = seed;
	        random = new Random(seed);

	        //create the start location
	        int length = 0;
	        length += buildStraight(0, width, true);

	        //create all of the medium sections
	        while (length < width - 64)
	        {
	        	int choice;
	        	if (this.difficulty <= 3) {
	        		System.out.println("Building Easy");
	        		length += buildEasy(length);
	        	}
	        	else if (this.difficulty > 3 && this.difficulty <= 5) {
	        		choice = random.nextInt(2);
	        		if (choice == 0) {
	        			System.out.println("Building Easy");
	        			length += buildEasy(length);
	        		}
	        		else if (choice == 1) {
	        			System.out.println("Building Medium");
	        			length += buildMedium(length);
	        		}
	        	}
	        	else if (this.difficulty > 5 && this.difficulty <=7) {
	        		System.out.println("Building Medium");
	        		length += buildMedium(length);
	        	}
	        	else if (this.difficulty > 7 && this.difficulty <= 10) {
	        		choice = random.nextInt(2);
	        		if (choice == 0) {
	        			System.out.println("Building Medium");
	        			length += buildMedium(length);
	        		}
	        		else if (choice == 1) {
	        			System.out.println("Building Hard");
	        			length += buildHard(length);
	        		}
	        	}
	        	else {
	        		System.out.println("Building Hard");
	        		length += buildHard(length);
	        	}
	            //if ((length + buildEasy(length)) > width - 64) {
	            	//buildStraight(length,width-length,false);
	            //}
	        	//length += buildZone(length, width - length);
				//length += buildStraight(length, width-length, false);
				//length += buildStraight(length, width-length, false);
				//length += buildHillStraight(length, width-length);
				//length += buildJump(length, width-length);
	        	length += buildEasy(length);
				//length += buildTubes(length, width-length);
				//length += buildCannons(length, width-length);
	        }
	        
	        

	        //set the end piece
	        int floor = height - 1 - random.nextInt(4);

	        xExit = length + 8;
	        yExit = floor;

	        // fills the end piece
	        for (int x = length; x < width; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                if (y >= floor)
	                {
	                    setBlock(x, y, GROUND);
	                }
	            }
	        }

	        if (type == LevelInterface.TYPE_CASTLE || type == LevelInterface.TYPE_UNDERGROUND)
	        {
	            int ceiling = 0;
	            int run = 0;
	            for (int x = 0; x < width; x++)
	            {
	                if (run-- <= 0 && x > 4)
	                {
	                    ceiling = random.nextInt(4);
	                    run = random.nextInt(4) + 4;
	                }
	                for (int y = 0; y < height; y++)
	                {
	                    if ((x > 4 && y <= ceiling) || x < 1)
	                    {
	                        setBlock(x, y, GROUND);
	                    }
	                }
	            }
	        }

	        fixWalls();

	    }
	    
	    private int buildEasy(int length) {
	    	int choice = random.nextInt(5);
	    	if (choice == 1) {
	    		return buildEasy1(length);
	    	}
	    	else if (choice == 2) {
	    		return buildEasy2(length);
	    	}
	    	else if (choice == 3) {
	    		return buildEasy3(length);
	    	}
	    	else if (choice == 4) {
	    		return buildEasy4(length);
	    	}
	    	else {
	    		return buildEasy5(length);
	    	}
	    }
	    
	    private int buildMedium(int length) {
	    	int choice = random.nextInt(5);
	    	if (choice == 1) {
	    		return buildMedium1(length);
	    	}
	    	else if (choice == 2) {
	    		return buildMedium2(length);
	    	}
	    	else if (choice == 3) {
	    		return buildMedium3(length);
	    	}
	    	else if (choice == 4) {
	    		return buildMedium4(length);
	    	}
	    	else {
	    		return buildMedium5(length);
	    	}
	    }
	    
	    private int buildHard(int length) {
	    	int choice = random.nextInt(5);
	    	if (choice == 1) {
	    		return buildHard1(length);
	    	}
	    	else if (choice == 2) {
	    		return buildHard2(length);
	    	}
	    	else if (choice == 3) {
	    		return buildHard3(length);
	    	}
	    	else if (choice == 4) {
	    		return buildHard4(length);
	    	}
	    	else {
	    		return buildHard5(length);
	    	}
	    }
	    
	    private int buildEasy1(int length) {
	    	int poop = 0;
	    	poop += buildStraight(length, width-length, false);
	    	poop += buildHillStraight(length+poop, width-length);
	    	poop += buildTubes(length+poop, width-length);
	    	return poop;
	    }
	    
	    private int buildEasy2(int length) {
	    	int poop = 0;
	    	poop += buildTubes(length, width-length);
	    	poop += buildStraight(length + poop, width-length, false);
	    	poop += buildTubes(length+poop, width-length);
	    	return poop;
	    }
	    
	    private int buildEasy3(int length) {
	    	int poop = 0;
	    	poop += buildHillStraight(length, width-length);
	    	poop += buildStraight(length+poop, width-length, false);
	    	poop += buildHillStraight(length+poop, width-length);
	    	return poop;
	    }
	    
	    private int buildEasy4(int length) {
	    	int poop = 0;
	    	poop += buildStraight(length, width-length, false);
	    	poop += buildTubes(length+poop, width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildEasy5(int length) {
	    	int poop = 0;
	    	poop += buildTubes(length,width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildMedium1(int length) {
	    	int poop = 0;
	    	poop += buildJump(length, width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	poop += buildCannons(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildMedium2(int length) {
	    	int poop = 0;
	    	poop += buildStraight(length,width-length,false);
	    	poop += buildJump(length+poop,width-length);
	    	poop += buildTubes(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildMedium3(int length) {
	    	int poop = 0;
	    	poop += buildCannons(length,width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	poop += buildTubes(length+poop, width-length);
	    	return poop;
	    }
	    
	    private int buildMedium4(int length) {
	    	int poop = 0;
	    	poop += buildHillStraight(length,width-length);
	    	poop += buildCannons(length+poop,width-length);
	    	poop += buildStraight(length+poop,width-length,false);
	    	return poop;
	    }
	    
	    private int buildMedium5(int length) {
	    	int poop = 0;
	    	poop += buildJump(length,width-length);
	    	poop += buildHillStraight(length+poop,width-length);
	    	poop += buildJump(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildHard1(int length) {
	    	int poop = 0;
	    	poop += buildStraight(length, width-length, false);
	    	poop += buildJump(length, width-length);
	    	poop += buildHillStraight(length+poop, width-length);
	    	poop += buildCannons(length+poop, width-length);
	    	poop += buildJump(length+poop, width-length);
	    	poop += buildCannons(length+poop, width-length);
	    	return poop;
	    }
	    
	    private int buildHard2(int length) {
	    	int poop = 0;
	    	poop += buildJump(length,width-length);
	    	poop += buildCannons(length+4,width-length);
	    	//addEnemyLine(length, length-width, 20);
	    	poop += buildCannons(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildHard3(int length) {
	    	int poop = 0;
	    	poop += buildJump(length,width-length);
	    	poop += buildHillStraight(length+poop, width-length);
	    	poop += buildJump(length+poop,width-length);
	    	return poop;
	    }
	    
	    private int buildHard4(int length) {
	    	int poop = 0;
	    	poop += buildTubes(length, width-length);
	    	poop += buildJump(length+poop,width-length);
	    	poop += buildTubes(length+poop,width-length);
	    	poop += buildCannons(length,width-length);
	    	return poop;
	    }
	    
	    private int buildHard5(int length) {
	    	int poop = 0;
	    	poop += buildJump(length, width-length);
	    	poop += buildJump(length+poop, width-length);
	    	poop += buildHillStraight(length+poop, width-length);
	    	poop += buildTubes(length+poop - 15, width-length);
	    	return poop;
	    }


	    private int buildJump(int xo, int maxLength)
	    {	gaps++;
	    	//jl: jump length
	    	//js: the number of blocks that are available at either side for free
	        int js = random.nextInt(4) + 2;
	        int jl = random.nextInt(2) + 2;
	        int length = js * 2 + jl;

	        boolean hasStairs = random.nextInt(3) == 0;

	        int floor = height - 1 - random.nextInt(4);
	      //run from the start x position, for the whole length
	        for (int x = xo; x < xo + length; x++)
	        {
	            if (x < xo + js || x > xo + length - js - 1)
	            {
	            	//run for all y's since we need to paint blocks upward
	                for (int y = 0; y < height; y++)
	                {	//paint ground up until the floor
	                    if (y >= floor)
	                    {
	                        setBlock(x, y, GROUND);
	                    }
	                  //if it is above ground, start making stairs of rocks
	                    else if (hasStairs)
	                    {	//LEFT SIDE
	                        if (x < xo + js)
	                        { //we need to max it out and level because it wont
	                          //paint ground correctly unless two bricks are side by side
	                            if (y >= floor - (x - xo) + 1)
	                            {
	                                setBlock(x, y, ROCK);
	                            }
	                        }
	                        else
	                        { //RIGHT SIDE
	                            if (y >= floor - ((xo + length) - x) + 2)
	                            {
	                                setBlock(x, y, ROCK);
	                            }
	                        }
	                    }
	                }
	            }
	        }

	        return length;
	    }

	    private int buildCannons(int xo, int maxLength)
	    {
	        int length = random.nextInt(10) + 2;
	        if (length > maxLength) length = maxLength;

	        int floor = height - 1 - random.nextInt(4);
	        int xCannon = xo + 1 + random.nextInt(4);
	        for (int x = xo; x < xo + length; x++)
	        {
	            if (x > xCannon)
	            {
	                xCannon += 2 + random.nextInt(4);
	            }
	            if (xCannon == xo + length - 1) xCannon += 10;
	            int cannonHeight = floor - random.nextInt(4) - 1;

	            for (int y = 0; y < height; y++)
	            {
	                if (y >= floor)
	                {
	                    setBlock(x, y, GROUND);
	                }
	                else
	                {
	                    if (x == xCannon && y >= cannonHeight)
	                    {
	                        if (y == cannonHeight)
	                        {
	                            setBlock(x, y, (byte) (14 + 0 * 16));
	                        }
	                        else if (y == cannonHeight + 1)
	                        {
	                            setBlock(x, y, (byte) (14 + 1 * 16));
	                        }
	                        else
	                        {
	                            setBlock(x, y, (byte) (14 + 2 * 16));
	                        }
	                    }
	                }
	            }
	        }

	        return length;
	    }

	    private int buildHillStraight(int xo, int maxLength)
	    {
	        int length = random.nextInt(10) + 10;
	        if (length > maxLength) length = maxLength;

	        int floor = height - 1 - random.nextInt(4);
	        for (int x = xo; x < xo + length; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                if (y >= floor)
	                {
	                    setBlock(x, y, GROUND);
	                }
	            }
	        }

	        addEnemyLine(xo + 1, xo + length - 1, floor - 1);

	        int h = floor;

	        boolean keepGoing = true;

	        boolean[] occupied = new boolean[length];
	        while (keepGoing)
	        {
	            h = h - 2 - random.nextInt(3);

	            if (h <= 0)
	            {
	                keepGoing = false;
	            }
	            else
	            {
	                int l = random.nextInt(5) + 3;
	                int xxo = random.nextInt(length - l - 2) + xo + 1;

	                if (occupied[xxo - xo] || occupied[xxo - xo + l] || occupied[xxo - xo - 1] || occupied[xxo - xo + l + 1])
	                {
	                    keepGoing = false;
	                }
	                else
	                {
	                    occupied[xxo - xo] = true;
	                    occupied[xxo - xo + l] = true;
	                    addEnemyLine(xxo, xxo + l, h - 1);

	                    //coins more likely if the player type is explorer
	                    if (isExplorer)
	                    {
	                        if(random.nextInt(6 - exploreVal) == 0){
		                        decorate(xxo - 1, xxo + l + 1, h);
		                        keepGoing = false;
		                    }
	                    }
	                    //otherwise standard probability
	                    else if(random.nextInt(4) == 0){
	                    	decorate(xxo - 1, xxo + l + 1, h);
		                    keepGoing = false;
	                    }
	                    for (int x = xxo; x < xxo + l; x++)
	                    {
	                        for (int y = h; y < floor; y++)
	                        {
	                            int xx = 5;
	                            if (x == xxo) xx = 4;
	                            if (x == xxo + l - 1) xx = 6;
	                            int yy = 9;
	                            if (y == h) yy = 8;

	                            if (getBlock(x, y) == 0)
	                            {
	                                setBlock(x, y, (byte) (xx + yy * 16));
	                            }
	                            else
	                            {
	                                if (getBlock(x, y) == HILL_TOP_LEFT) setBlock(x, y, HILL_TOP_LEFT_IN);
	                                if (getBlock(x, y) == HILL_TOP_RIGHT) setBlock(x, y, HILL_TOP_RIGHT_IN);
	                            }
	                        }
	                    }
	                }
	            }
	        }

	        return length;
	    }

	    private void addEnemyLine(int x0, int x1, int y)
	    {

	        for (int x = x0; x < x1; x++)
	        {
	        	//enemy generation rate linked to percentage enemies killed in last level
	        	int thresh = random.nextInt(55 - (int)(5*killerVal));
	            if (thresh < difficulty)
	            {
	            	type = generateRandomEnemy(difficulty);


	                setSpriteTemplate(x, y, new SpriteTemplate(type, random.nextInt(35) < difficulty));
	                ENEMIES++;
	            }
	        }
	    }

	    private int buildTubes(int xo, int maxLength)
	    {
	        int length = random.nextInt(10) + 5;
	        if (length > maxLength) length = maxLength;

	        int floor = height - 1 - random.nextInt(4);
	        int xTube = xo + 1 + random.nextInt(4);
	        int tubeHeight = floor - random.nextInt(2) - 2;
	        for (int x = xo; x < xo + length; x++)
	        {
	            if (x > xTube + 1)
	            {
	                xTube += 3 + random.nextInt(4);
	                tubeHeight = floor - random.nextInt(2) - 2;
	            }
	            if (xTube >= xo + length - 2) xTube += 10;

	            if (x == xTube && random.nextInt(11) < difficulty + 1)
	            {
	                setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
	                ENEMIES++;
	            }

	            for (int y = 0; y < height; y++)
	            {
	                if (y >= floor)
	                {
	                    setBlock(x, y,GROUND);

	                }
	                else
	                {
	                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
	                    {
	                        int xPic = 10 + x - xTube;

	                        if (y == tubeHeight)
	                        {
	                        	//tube top
	                            setBlock(x, y, (byte) (xPic + 0 * 16));
	                        }
	                        else
	                        {
	                        	//tube side
	                            setBlock(x, y, (byte) (xPic + 1 * 16));
	                        }
	                    }
	                }
	            }
	        }

	        return length;
	    }

	    private int buildStraight(int xo, int maxLength, boolean safe)
	    {
	        int length = random.nextInt(10-this.difficulty) + 2;

	        if (safe)
	        	length = 10 + random.nextInt(5);

	        if (length > maxLength)
	        	length = maxLength;

	        int floor = height - 1 - random.nextInt(4);

	        //runs from the specified x position to the length of the segment
	        for (int x = xo; x < xo + length; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                if (y >= floor)
	                {
	                    setBlock(x, y, GROUND);
	                }
	            }
	        }

	        if (!safe)
	        {
	            if(isExplorer){
		            if (length > 5)
		            {
		                decorate(xo, xo + length, floor);
		            }
		        }
		        else if(random.nextInt(2) == 0){
		        	if (length > 5)
		            {
		                decorate(xo, xo + length, floor);
		            }
		        }
	        }

	        return length;
	    }

	    private void decorate(int xStart, int xLength, int floor)
	    {
	    	//if its at the very top, just return
	        if (floor < 1)
	        	return;

	        //        boolean coins = random.nextInt(3) == 0;
	        boolean rocks = true;

	        //add an enemy line above the box
	        addEnemyLine(xStart + 1, xLength - 1, floor - 1);

	        //longer coin sequences for an explorer
	        int s;
	        int e;
	        if(isExplorer){
	        	s = random.nextInt(6-exploreVal);
	        	e = random.nextInt(6-exploreVal);
	        }
	        else{
	        	s = random.nextInt(4)+random.nextInt(2);
	        	e = random.nextInt(4)+random.nextInt(2);
	        }

	        if (floor - 2 > 0){
	            if ((xLength - 1 - e) - (xStart + 1 + s) > 1){
	                for(int x = xStart + 1 + s; x < xLength - 1 - e; x++){
	                    setBlock(x, floor - 2, COIN);
	                    COINS++;
	                }
	            }
	        }
	        //longer block sequences for the explorer
	        if(isExplorer){
	        	s = random.nextInt(6-exploreVal);
	        	e = random.nextInt(6-exploreVal);
	        }
	        else{
	        	s = random.nextInt(4)+random.nextInt(2);
	        	e = random.nextInt(4)+random.nextInt(2);
	        }
	        
	        //this fills the set of blocks and the hidden objects inside them
	        if (floor - 4 > 0)
	        {
	            //if we're going to populate a series of more than two blocks
	            if ((xLength - 1 - e) - (xStart + 1 + s) > 2)
	            {
	                for (int x = xStart + 1 + s; x < xLength - 1 - e; x++)
	                {
	                    //this is pretty useless since its set to true
	                    if (rocks)
	                    {
	                        //if were past xStart + 1 && before xLength-2 && 1/3 chance of true
	                        if (x != xStart + 1 && x != xLength - 2 && random.nextInt(3) == 0)
	                        {
	                            if (random.nextInt(4) == 0)
	                            {
	                                //fills block with mushroom
	                                setBlock(x, floor - 4, BLOCK_POWERUP);
	                                BLOCKS_POWER++;
	                            }
	                            else
	                            {	//the fills a block with a hidden coin
	                                setBlock(x, floor - 4, BLOCK_COIN);
	                                BLOCKS_COINS++;
	                            }
	                        }
	                        //otherwise 25% chance
	                        else if (random.nextInt(4) == 0)
	                        {
	                        	//25 percent chance
	                            if (random.nextInt(4) == 0)
	                            {
	                                //fire flower
	                                setBlock(x, floor - 4, (byte) (2 + 1 * 16));
	                                BLOCKS_POWER++;
	                            }
	                            else
	                            {
	                                //coin
	                                setBlock(x, floor - 4, (byte) (1 + 1 * 16));
	                                BLOCKS_COINS++;
	                            }
	                        }
	                        else
	                        {
	                            //otherwise empty block
	                            setBlock(x, floor - 4, BLOCK_EMPTY);
	                            BLOCKS_EMPTY++;
	                        }
	                    }
	                }
	            }
	        }
	    }

	    private void fixWalls()
	    {
	        boolean[][] blockMap = new boolean[width + 1][height + 1];

	        for (int x = 0; x < width + 1; x++)
	        {
	            for (int y = 0; y < height + 1; y++)
	            {
	                int blocks = 0;
	                for (int xx = x - 1; xx < x + 1; xx++)
	                {
	                    for (int yy = y - 1; yy < y + 1; yy++)
	                    {
	                        if (getBlockCapped(xx, yy) == GROUND){
	                        	blocks++;
	                        }
	                    }
	                }
	                blockMap[x][y] = blocks == 4;
	            }
	        }
	        blockify(this, blockMap, width + 1, height + 1);
	    }

	    private void blockify(Level level, boolean[][] blocks, int width, int height){
	        int to = 0;
	        if (type == LevelInterface.TYPE_CASTLE)
	        {
	            to = 4 * 2;
	        }
	        else if (type == LevelInterface.TYPE_UNDERGROUND)
	        {
	            to = 4 * 3;
	        }

	        boolean[][] b = new boolean[2][2];

	        for (int x = 0; x < width; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                for (int xx = x; xx <= x + 1; xx++)
	                {
	                    for (int yy = y; yy <= y + 1; yy++)
	                    {
	                        int _xx = xx;
	                        int _yy = yy;
	                        if (_xx < 0) _xx = 0;
	                        if (_yy < 0) _yy = 0;
	                        if (_xx > width - 1) _xx = width - 1;
	                        if (_yy > height - 1) _yy = height - 1;
	                        b[xx - x][yy - y] = blocks[_xx][_yy];
	                    }
	                }

	                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
	                {
	                    if (b[0][0] == b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                            // KEEP OLD BLOCK!
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                        	//down grass top?
	                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                        	//up grass top
	                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
	                {
	                    if (b[0][0])
	                    {
	                    	//right grass top
	                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));
	                    }
	                    else
	                    {
	                    	//left grass top
	                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));
	                    }
	                }
	                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
	                {
	                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                }
	                else if (b[0][0] == b[1][0])
	                {
	                    if (b[0][0])
	                    {
	                        if (b[0][1])
	                        {
	                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][1])
	                        {
	                        	//right up grass top
	                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to));
	                        }
	                        else
	                        {
	                        	//left up grass top
	                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][1] == b[1][1])
	                {
	                    if (b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                        	//left pocket grass
	                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                        	//right pocket grass
	                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to));
	                        }
	                    }
	                }
	                else
	                {
	                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to));
	                }
	            }
	        }
	    }
	    
	    public RandomLevel clone() throws CloneNotSupportedException {

	    	RandomLevel clone=new RandomLevel(width, height);
	    	clone.setDifficulty(this.difficulty);
	    	clone.xExit = xExit;
	    	clone.yExit = yExit;
	    	byte[][] map = getMap();
	    	SpriteTemplate[][] st = getSpriteTemplate();
	    	
	    	for (int i = 0; i < map.length; i++)
	    		for (int j = 0; j < map[i].length; j++) {
	    			clone.setBlock(i, j, map[i][j]);
	    			clone.setSpriteTemplate(i, j, st[i][j]);
	    	}
	    	clone.BLOCKS_COINS = BLOCKS_COINS;
	    	clone.BLOCKS_EMPTY = BLOCKS_EMPTY;
	    	clone.BLOCKS_POWER = BLOCKS_POWER;
	    	clone.ENEMIES = ENEMIES;
	    	clone.COINS = COINS;
	    	
	        return clone;

	      }

	    public void evaluatePlayer(){
	    	//first lets evaluate the player skill

	    	//get the ratio of completion time to total time
	    	//System.out.println(playerMetrics.completionTime+"/"+playerMetrics.totalTime);
	    	double timeRatio = (double)playerMetrics.completionTime/(double)playerMetrics.totalTime;
	    	System.out.println("Time ratio - "+timeRatio);
	    	double deathRatio = (double)(3 - playerMetrics.totalDeaths)/(double)(3);
	    	System.out.println("Death ratio - "+deathRatio);

	    	double skillRatio = deathRatio * timeRatio;
	    	System.out.println("Skill ratio "+skillRatio);

	    	//the closer this is to one, the better
	    	if(skillRatio > 0.75){
	    		playerSkill = Skill.EXCELLENT;
	    		this.difficulty += 2;
	    	}
	    	else if(skillRatio > 0.5 && skillRatio < 0.75){
	    		playerSkill = Skill.GOOD;
	    		this.difficulty++;
	    	}
	    	else if(skillRatio < 0.5 && skillRatio > 0.25){
	    		playerSkill = Skill.AVERAGE;
	    	}
	    	else{
	    		playerSkill = Skill.BAD;
	    		this.difficulty -= 1;
	    	}

	    	//clamp difficulty to 0
	    	if(this.difficulty < 0){
	    		this.difficulty = 0;
	    	}

	    	//now we need to evaluate what sort of player we have
	    	System.out.println("~~~~~~~~~~~~~~~~");
	    	System.out.println("Previous level difficulty "+playerMetrics.difficulty);
	    	System.out.println("Current difficulty - "+this.difficulty);
	    	evaluateKiller();
	    	System.out.println("~~~~~~~~~~~~~~~~");
	    	evaluateExplorer();
	    	System.out.println("~~~~~~~~~~~~~~~~");
	    	evaluateRunner();
	    }

	    public void evaluateKiller(){
	    	double killRatio = (double)playerMetrics.totalKills/(double)playerMetrics.totalEnemies;
	    	
	    		//isKiller = true;
	    	killerVal = killRatio;
	    	System.out.println("Kill val is - "+killerVal);
	    }

	    public void evaluateExplorer(){
	    	double blockRatio = playerMetrics.percentageBlocksDestroyed;
	    	double coinRatio = (double)playerMetrics.coinsCollected/(double)playerMetrics.totalCoins;
	    	double exploreRatio = blockRatio * coinRatio;
	    	System.out.println("Explore ratio - "+exploreRatio);
	    	if(exploreRatio > 0.25)
	    		isExplorer = true;
	    	else
	    		isExplorer = false;

	    	if(exploreRatio > 0.75)
	    		exploreVal = 4;
	    	else if(exploreRatio > 0.5)
	    		exploreVal = 3;
	    	else if(exploreRatio > 0.25)
	    		exploreVal = 2;
	    	else
	    		exploreVal = 1;

	    	System.out.println("Explorer status - "+isExplorer); 

	    }

	    public void evaluateRunner(){
	    	double avgRunTime = (double)playerMetrics.timeSpentRunning/(double)playerMetrics.totalDeaths;
	    	double avgTime = (double)playerMetrics.totalTime/(double)playerMetrics.totalDeaths;
	    	System.out.println("Completion time - "+playerMetrics.completionTime);
	    	System.out.println("Average time - "+avgTime);
	    	System.out.println("Avg Run time - "+avgRunTime);
	    	System.out.println("completion time - "+playerMetrics.completionTime);
	    	

	    	double runRatio = avgRunTime/avgTime;
	    	System.out.println("Run ratio - "+runRatio);


	    	//temporary speed run decision
	    	if(runRatio >= 0.1)
	    		isRunner = true;
	    	else
	    		isRunner = false;
	    }

	    public int getDifficulty(){
	    	return this.difficulty;
	    }

	    private int generateRandomEnemy(int difficulty){
	    	int type = Enemy.ENEMY_GOOMBA;

	    	if(random.nextInt((int)Math.pow(difficulty,1.75)) < difficulty){
	    		type = Enemy.ENEMY_GOOMBA;
	    	}
	    	else if(random.nextInt((int)Math.pow(difficulty,1.65)) < difficulty){
	    		type = random.nextInt(2);
	    	}
	    	else if(random.nextInt((int)Math.pow(difficulty,1.5)) < difficulty){
	    		type = 3;
	    	}
	    	else{
	    		type = Enemy.ENEMY_GOOMBA;
	    	}
	    	return type;

	    }

}

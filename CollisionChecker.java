public class CollisionChecker
{
    public GamePanel gp;
    public CollisionChecker(GamePanel gp)
    {
        this.gp=gp;
    }
    public void checkTile(Entity entity)
    {
        int entityLeftWorldX=entity.worldX+entity.solidArea.x;//col(23)+8px(shift);
        int entityRightWorldX=entity.worldX+entity.solidArea.x+entity.solidArea.width;//col(23)+8px(shift)+32px(solid area)
        int entityTopWorldY=entity.worldY+entity.solidArea.y;//row(21)+16px(shift)
        int entityBottomWorldY=entity.worldY+entity.solidArea.y+entity.solidArea.height;//row(21)+16px(shift)+32px(solid area)

        //world row col occupied by player rectangle
        int entityLeftCol=entityLeftWorldX/gp.tileSize;//nearly col(23)
        int entityRightCol=entityRightWorldX/gp.tileSize;//nearly col(24)
        int entityTopRow=entityTopWorldY/gp.tileSize;//nearly row(21)
        int entityBottomRow=entityBottomWorldY/gp.tileSize;//nearly row(22)


        int tileNum1,tileNum2;//for direction up or down, tileNum1 and tileNum2 checks two tiles are colliding left and right respectively
        switch(entity.direction)
        {
            case "up":
            entityTopRow=(entityTopWorldY-entity.speed)/gp.tileSize;
            tileNum1=gp.tile.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2=gp.tile.mapTileNum[entityRightCol][entityTopRow];
            if(gp.tile.tile[tileNum1].collision==true && gp.tile.tile[tileNum2].collision==true)
            {
                entity.collisionOn=true;
            }
            break;
            case "down":
            entityBottomRow=(entityBottomWorldY+entity.speed)/gp.tileSize;
            tileNum1=gp.tile.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2=gp.tile.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tile.tile[tileNum1].collision==true || gp.tile.tile[tileNum2].collision==true)
            {
                entity.collisionOn=true;
            }
            break;
            case "left":
            entityLeftCol=(entityLeftWorldX-entity.speed)/gp.tileSize;
            tileNum1=gp.tile.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2=gp.tile.mapTileNum[entityLeftCol][entityBottomRow];
            if(gp.tile.tile[tileNum1].collision==true || gp.tile.tile[tileNum2].collision==true)
            {
                entity.collisionOn=true;
            }
            break;
            case "right":
            entityRightCol=(entityRightWorldX+entity.speed)/gp.tileSize;
            tileNum1=gp.tile.mapTileNum[entityRightCol][entityTopRow];
            tileNum2=gp.tile.mapTileNum[entityRightCol][entityBottomRow];
            if(gp.tile.tile[tileNum1].collision==true || gp.tile.tile[tileNum2].collision==true)
            {
                entity.collisionOn=true;
            }
            break;
        }
    }
    public int checkObject(Entity entity,boolean player)//in this checkObject(),
    // rectangles's x,y are increased,decreased to check whether two rectangles are colliding or not
    {
        int index=999;
        for(int i=0;i<gp.obj.length;i++)
        {
            if(gp.obj[i]!=null)
            {
                //Get entity's solid area position
                entity.solidArea.x=entity.worldX+entity.solidArea.x;//entity.solidArea.x act like worldX
                entity.solidArea.y=entity.worldY+entity.solidArea.y;//entity.solidArea.y act like worldY
                //Get the object's solid area position
                gp.obj[i].solidArea.x=gp.obj[i].worldX+gp.obj[i].solidArea.x;//obj[i] solidArea.x is now the  location x
                gp.obj[i].solidArea.y=gp.obj[i].worldY+gp.obj[i].solidArea.y;//obj[i] solidArea.y now the location y
                switch(entity.direction)
                {
                    case "up":
                    entity.solidArea.y-=entity.speed;//rectangle's y is decreasing ,not wordY is decreasing
                    if(entity.solidArea.intersects(gp.obj[i].solidArea))//rectangle.intersects(rectangle) checks two rectangles are colliding or not
                    {
                        if(gp.obj[i].collision==true)
                        {
                            entity.collisionOn=true;
                        }
                        if(player==true)//may npc collide
                        {
                            index=i;
                        }
                    }
                    break;
                    case "down":
                    entity.solidArea.y+=entity.speed;//rectangle's y increasing,not worldY is increasing
                    if(entity.solidArea.intersects(gp.obj[i].solidArea))
                    {
                        if(gp.obj[i].collision==true)
                        {
                            entity.collisionOn=true;
                        }
                        if(player==true)
                        {
                            index=i;
                        }
                    }
                    break;
                    case "left":
                    entity.solidArea.x-=entity.speed;//rectangle's x decreasing ,not worldX decreasing
                    if(entity.solidArea.intersects(gp.obj[i].solidArea))
                    {
                        if(gp.obj[i].collision==true)
                        {
                            entity.collisionOn=true;
                        }
                        if(player==true)
                        {
                            index=i;
                        }
                    }
                    break;
                    case "right":
                    entity.solidArea.x+=entity.speed;//rectangle's x increasing ,not wolrdX increasi
                    if(entity.solidArea.intersects(gp.obj[i].solidArea))
                    {
                        if(gp.obj[i].collision=true)
                        {
                            entity.collisionOn=true;
                        }
                        if(player==true)
                        {
                            index=i;
                        }
                    }
                    break;
                }
                entity.solidArea.x=entity.solidAreaDefaultX;//solidArea.x range is changed from 8 px to 8px +worldX,resetting to 8
                entity.solidArea.y=entity.solidAreaDefaultY;//solidArea.y range is changed from 16 px to 16px+worldY px to else,resetting to 16
                gp.obj[i].solidArea.x=gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y=gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
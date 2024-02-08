#include <iostream>

using namespace std;


void hangman(int lol)
{
    switch(lol)
    {
            case 0:
        cout<<"  ____________      "<<endl;
        cout<<" |       |          "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |                  "<<endl;
        cout<<" |__________        "<<endl;
        break;

            case 1:
      cout<<"  ____________      "<<endl;
      cout<<" |        |     "<<endl;
      cout<<" |        ☹    "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |__________        "<<endl;
      
         break;

                
            case 2:
     cout<<"  ____________      "<<endl;
      cout<<" |        |     "<<endl;
      cout<<" |        ☹    "<<endl;
      cout<<" |        |     "<<endl;
      cout<<" |        |     "<<endl;
      cout<<" |        |     "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |                  "<<endl;
      cout<<" |__________        "<<endl;
      
         break;

                
            case 3:
    cout<<"  ____________      "<<endl;
     cout<<" |        |     "<<endl;
     cout<<" |        ☹    "<<endl;
     cout<<" |        |     "<<endl;
     cout<<" |       *|     "<<endl;
     cout<<" |      * |     "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |__________        "<<endl;
     
         break;

                
            case 4:
     cout<<"  ____________      "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |          ☹    "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |         *|*    "<<endl;
     cout<<" |        * | *   "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |__________        "<<endl;
    
         break;

            case 5:
     cout<<"  ____________      "<<endl;
     cout<<" |        |     "<<endl;
     cout<<" |        ☹    "<<endl;
     cout<<" |        |     "<<endl;
     cout<<" |       *|*    "<<endl;
     cout<<" |      * | *   "<<endl;
     cout<<" |       *      "<<endl;
     cout<<" |      *       "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |__________        "<<endl;
        break;
            case 6:
     cout<<"  ____________      "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |          ☹    "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |         *|*    "<<endl;
     cout<<" |        * | *   "<<endl;
     cout<<" |         * *    "<<endl;
     cout<<" |        *   *   "<<endl;
     cout<<" |                  "<<endl;
     cout<<" |__________        "<<endl;
     
         break;

            
            case 7:
     cout<<"  ____________      "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |          ☹    "<<endl;
     cout<<" |          |     "<<endl;
     cout<<" |         *|*    "<<endl;
     cout<<" |        * | *   "<<endl;
     cout<<" |         * *    "<<endl;
     cout<<" |        *   *   "<<endl;
     cout<<" |        🔥🔥🔥🔥  "<<endl;
     cout<<" |________🔥🔥🔥🔥 "<<endl; 
     
         Break;
  cout<<"   0     1     2  "<<endl; 
    cout<<" -----|-----|-----"<<endl; 
    cout<<"0  -  |  -  |  -  "<<endl; 
    cout<<" _____|_____|_____"<<endl; 
    cout<<"      |     |     "<<endl; 
    cout<<"1  -  |  -  |  -  "<<endl; 
    cout<<" _____|_____|_____"<<endl; 
    cout<<"      |     |     "<<endl; 
    cout<<"2  -  |  -  |  -  "<<endl; 
    cout<<"      |     |     "<<endl; 


    }
}

int main()
{
    int i, lives = 0;
    char word2[4] = {'_','_','_','_'};
    char ans, total = 0; 
    
    //image
    cout<<"  ___________ "<<endl;
    cout<<"  |           "<<endl;
    cout<<"  |           "<<endl;
    cout<<"  |           "<<endl;
    cout<<"  |           "<<endl;
    cout<<"  |           "<<endl;
    cout<<"__|__         "<<endl<<endl;
    
    
    for(i=0; i<7; i++)
    {
    //guess
    cout<<"Enter a letter"<<endl;
    cin>>ans;
            
        if(ans == 'f' || ans == 'o' || ans == 'u' || ans == 'r')
            {
        START:
        switch(ans)
        {
            //if lette guess is right
                case 'f':
                hangman(lives);
                word2[0] = 'f';
                cout<<word2<<endl;
                total = total + 2;
                break; 
                case 'o':
                hangman(lives);
                word2[1] = 'o';
                cout<<word2<<endl;
                total = total + 2;
                    break; 
                    case 'u':
                    hangman(lives);
                    word2[2] = 'u';
                    cout<<word2<<endl;
                    total = total + 2;
                    break; 
                    case 'r':
                    hangman(lives);
                    word2[3] = 'r';
                    cout<<word2<<endl;
                    total = total + 2;
                    break;
            }
                if(total == 8)
                {
                cout<<"The word is four"<<endl;
                return 0;
                }
            }
        else
        {
            //if guesses are right
            while(ans != 'f'||ans != 'o'||ans != 'u'|| ans != 'r' && lives < 7)
            {
                lives = lives + 1;
                if(lives == 7)
                {
                    hangman(lives);
                    cout<<"GAME OVER"<<endl;
                   return 0;
                    
                }
                    //if answer is not right
                    hangman(lives);
                    cout<<word2<<endl;
                    cout<<"Try again bozo"<<endl;
                    cin>>ans;
                    
                    //my loop 
                     if(ans == 'f' || ans == 'o' || ans == 'u' || ans == 'r' )
                    {
                        goto START;
                    }
                }
            }
        }

    return 0;
    }



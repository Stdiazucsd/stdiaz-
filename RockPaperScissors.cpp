#include <iostream>
#include <stdlib.h>
#include <time.h>

using namespace std;

void score (char array[5])
{
  int i = 0, j = 0, p = 0,c = 0;

  while (i < 5)
    {
      if ('p' == array[i])
	{
	  p++;
	}
      else
	{
	  p - 1;
	}
      i++;
    }
    
     while (j < 5)
    {
      if ('c' == array[j])
	{
	  c++;
	}
      else
	{
	  c - 1;
	}
      j++;
    }
    
    
    
  if (p < 0)
    {
      cout << "You won 0/5 games" << endl;
    }
  else
    {
      cout << "You won " << p << "/5 games" << endl;
    }
    
    if (c <0) 
     {
       cout << "I won 0/5 games" << endl;
     }
    else
     {
       cout << "I won " << c<< "/5 games" << endl;
     }
}

char pick ()
{
  char choice;

  cout << "Your choice r(Rock) p(Paper) s(Scissors):";

  cin >> choice;
  return choice;
}

char compick ()
{
  char a;
  int (numb) = rand () % 3 + 1;
  if (numb == 1) return 'p';
  if (numb == 2) return 'r';
  if (numb == 3) return 's';
  return a;
}

void select (char ans)
{
  if (ans == 'p')
    cout << "Paper" << endl;
  if (ans == 's')
    cout << "Scissors" << endl;
  if (ans == 'r')
    cout << "Rock" << endl;

}

char winner (char playerc, char compc)
{
    //my partner and I worked on this function together
  char p = 'p', c = 'c', n = 'n';

  char rock = 'r', paper = 'p', scissors = 's';
  
  if (playerc == paper && compc == rock)
    {
      cout << "You win Paper beats Rock" << endl;
      cout << "------------------------------------------" << endl;
      return p;
    }
  else if (playerc == scissors && compc == paper)
    {
      cout << "You win Scissors rips Paper" << endl;
      cout << "------------------------------------------" << endl;
      return p;
    }
  else if (playerc == rock && compc == scissors)
    {
      cout << "You win rock breaks Scissors" << endl;
      cout << "------------------------------------------" << endl;
      return p;
    }
    else if (playerc == rock && compc == paper)
    {
      cout << "I win Paper beats Rock" << endl;
      cout << "------------------------------------------" << endl;
      return c;
    }
  else if (playerc == paper && compc == scissors)
    {
      cout << "I win Scissors rips Paper" << endl;
      cout << "------------------------------------------" << endl;
      return c;
    }
  else if (playerc == scissors && compc == rock)
    {
      cout << "I win Rock beats up Scissors" << endl;
      cout << "------------------------------------------" << endl;
      return c;
    }
  else
    {
      cout << "Its a tie" << endl;
      cout<<"------------------------------------------"<<endl;
      return n;
    }
  return n;
}

int main ()
{
  char playerc, compc, recivint;
  char array[5];

  cout << "Welcome to Rock Paper Scissors" << endl;
  cout << "You will be going up against me the computer" << endl;
  cout << "------------------------------------------" << endl;

  for (int i = 0; i < 5; i++)
    {
      playerc = pick ();
      cout << "Your pick was: ";
      select (playerc);

      cout << "I chose: ";
      compc = compick ();
      select (compc);

      recivint = winner (playerc, compc);
      array[i] = recivint;

    }
    cout << endl << endl;
    score (array);

  return 0;
}


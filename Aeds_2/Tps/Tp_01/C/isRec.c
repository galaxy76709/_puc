// dependencias
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define null NULL

int length ( char str [ ] );

char lower ( char c )
{
    if ( c >= 'A' && c <= 'Z' )
    {
        return (char) ( c + ('a' - 'A') );
    }
    return c;
}

bool theEnd ( char str [ ] )
{
    if ( str == NULL )
    {
        return false;
    }

    int size = length ( str );
    if ( size == 3 &&
         str[0] == 'F' &&
         str[1] == 'I' &&
         str[2] == 'M' )
    {
        return true;
    }

    return false;
}

int length ( char str [ ] )
{
    if ( str == NULL )
    {
        return 0;
    }

    int i = 0;
    while ( str[i] != '\0' )
    {
        i++;
    }
    return i;
}

bool isVogal ( char str [ ] )
{
    int size = length ( str );
    if ( str == NULL || size <= 0 )
    {
        return false;
    }

    bool hasChar = false;
    for ( int i = 0; i < size; i++ )
    {
        char c = lower ( str[i] );
        if ( c >= 'a' && c <= 'z' )
        {
            if ( c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' )
            {
                hasChar = true;
                continue;
            }
        }

        return false;
    }

    return hasChar;
}

bool isConsoante ( char str [ ] )
{
    int size = length ( str );
    if ( str == NULL || size <= 0 )
    {
        return false;
    }

    bool hasChar = false;
    for ( int i = 0; i < size; i++ )
    {
        char c = lower ( str[i] );
        if ( c >= 'a' && c <= 'z' )
        {
            if ( c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' )
            {
                hasChar = true;
                continue;
            }
        }

        return false;
    }

    return hasChar;
}

bool isNumero ( char str [ ] )
{
    if ( str == NULL || str[0] == '\0' )
    {
        return false;
    }

    int i = 0;
    if ( str[i] == '+' || str[i] == '-' )
    {
        i++;
    }

    if ( str[i] == '\0' )
    {
        return false;
    }

    bool hasDigit = false;
    for ( ; str[i] != '\0'; i++ )
    {
        if ( str[i] < '0' || str[i] > '9' )
        {
            return false;
        }
        hasDigit = true;
    }

    return hasDigit;
}

bool isNatural ( char str [ ] )
{
    if ( str == NULL || str[0] == '\0' )
    {
        return false;
    }

    int i = 0;
    bool hasDigit = false;
    bool hasSeparator = false;

    if ( str[i] == '+' || str[i] == '-' )
    {
        i++;
    }

    if ( str[i] == '\0' )
    {
        return false;
    }

    while ( str[i] != '\0' )
    {
        if ( str[i] == '.' || str[i] == ',' )
        {
            if ( hasSeparator )
            {
                return false;
            }
            hasSeparator = true;
        }
        else if ( str[i] >= '0' && str[i] <= '9' )
        {
            hasDigit = true;
        }
        else
        {
            return false;
        }

        i++;
    }

    return hasDigit;
}

int main ( void )
{
    char str[10000];

    while ( fgets ( str, sizeof(str), stdin ) != NULL )
    {
        int size = length ( str );
        if ( size > 0 && str[size - 1] == '\n' )
        {
            str[size - 1] = '\0';
            size--;
        }

        if ( size > 0 && str[size - 1] == '\r' )
        {
            str[size - 1] = '\0';
            size--;
        }

        if ( theEnd ( str ) )
        {
            break;
        }

        bool X1 = isVogal ( str );
        bool X2 = isConsoante ( str );
        bool X3 = isNumero ( str );
        bool X4 = isNatural ( str );

        printf ( "%s %s %s %s\n",
                 ( X1 ? "SIM" : "NAO" ),
                 ( X2 ? "SIM" : "NAO" ),
                 ( X3 ? "SIM" : "NAO" ),
                 ( X4 ? "SIM" : "NAO" ) );
    }

    return 0;
}

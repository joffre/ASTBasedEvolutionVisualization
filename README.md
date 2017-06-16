# ASTBasedEvolutionVisualization

NEW VERSION :
Deploy WAR file on your Tomcat or Wildfly server, start it, log with github credentials and enjoy !

Next version soon.

OLD VERSION : 
Pour que git sache que vous utilisez un autre outil de diff, ajouter ces lignes à votre `~/.gitconfig`:

    [difftool "ast_cmp"]
    cmd = bash /path/to/ast-difftool.sh cmp $LOCAL $REMOTE   


Usage:

    git difftool -y --tool=ast_cmp commitA commitB

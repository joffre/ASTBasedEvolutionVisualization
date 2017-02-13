# ASTBasedEvolutionVisualization


Pour que git sache que vous utilisez un autre outil de diff, ajouter ces lignes à votre `~/.gitconfig`:

    [difftool "ast_cmp"]
    cmd = bash /path/to/ast-difftool.sh cmp $LOCAL $REMOTE

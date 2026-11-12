# Spec gate

Checklist for reviewing the pull request of the analysis phase (proposal, specs,
design, tasks and feature files) before any implementation starts.

The same checklist is used for every agent, so results are comparable.

## How to use it

1. Run the automatic checks. If any fails, stop: the PR goes back without manual review.
2. Review every item. Each item is either PASS or FAIL; there is no partial pass.
3. Any FAIL means "request changes". Write the item number and the reason in the PR.
4. The gate passes only when every item passes.
5. Record the result at the end of this file's template in the PR description.

## 0. Automatic checks

- [ ] 0.1 `openspec validate <change-id> --strict` passes.
- [ ] 0.2 `./mvnw verify` passes.
- [ ] 0.3 The PR only touches `openspec/changes/<change-id>/` and
      `src/test/resources/features/`. No production code, no other test code.

## 1. Traceability and scope

- [ ] 1.1 The proposal references the source request by path.
- [ ] 1.2 Every requirement can be traced to a sentence of the request.
- [ ] 1.3 Nothing is added that the request does not ask for
      (fields, endpoints, statuses, options, behaviors).
- [ ] 1.4 "Out of scope" exists and is consistent with the request.
- [ ] 1.5 "Open questions" exists. Ambiguities are listed there, not resolved
      silently. Any question that blocks implementation is answered before passing.

## 2. Specs

- [ ] 2.1 Requirements use SHALL or MUST.
- [ ] 2.2 Every requirement has at least one scenario.
- [ ] 2.3 Validation and failure scenarios are covered, not only the happy path.
- [ ] 2.4 Scenarios describe observable behavior (inputs, outputs, HTTP status codes),
      not implementation.
- [ ] 2.5 Scenarios use concrete values and have a single possible interpretation.

## 3. Feature files

- [ ] 3.1 One file per capability in `src/test/resources/features/<capability>/`.
- [ ] 3.2 Each feature file is tagged `@pending`.
- [ ] 3.3 Scenarios map 1:1 to spec scenarios: same count, same titles.
- [ ] 3.4 Steps are written at API level, with no class names, SQL or other
      implementation details.

## 4. Design

- [ ] 4.1 Ports, adapters and classes are named and placed according to the
      hexagonal structure in `AGENTS.md`.
- [ ] 4.2 The HTTP contract is complete: method, path, request body, response body,
      status codes and `ProblemDetail` errors.
- [ ] 4.3 Flyway migrations are defined with tables, columns, types and constraints,
      and constraints match the validation rules of the specs.
- [ ] 4.4 No new dependencies, or each one is justified.
- [ ] 4.5 Nothing contradicts `AGENTS.md` (stack, architecture, testing, boundaries).

## 5. Tasks

- [ ] 5.1 Each task is small enough for one commit and leaves `./mvnw verify` passing.
- [ ] 5.2 Each task names the test written before the production code.
- [ ] 5.3 Domain rules that constrain input values have a property-based test task.
- [ ] 5.4 The last task removes the `@pending` tag, and that is the only change
      to feature files.
- [ ] 5.5 No task touches protected files (see Boundaries in `AGENTS.md`).

## Result template

Copy into the PR description when the review is finished.

```
Spec gate result
- Change: <change-id>
- Request: requests/<id>-<slug>.md
- Agent / model: <tool> / <model and version>
- Base version: <tag>
- Review rounds until PASS: <n>
- Failed items per round: <round 1: 1.3, 4.2; round 2: none>
- Notes: <anything worth comparing between agents>
```